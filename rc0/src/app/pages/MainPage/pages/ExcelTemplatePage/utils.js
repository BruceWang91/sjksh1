import Handsontable from "handsontable";
const XLSX = require('xlsx')

const SELECTED_CLASS = "selected";
const ODD_ROW_CLASS = "odd";
const headerAlignments = new Map([
  ["9", "htCenter"],
  ["10", "htRight"],
  ["12", "htCenter"]
]);


export const addClassesToRows = (
  TD,
  row,
  column,
  prop,
  value,
  cellProperties
) => {
  // Adding classes to `TR` just while rendering first visible `TD` element
  if (column !== 0) {
    return;
  }

  const parentElement = TD.parentElement;

  if (parentElement === null) {
    return;
  }

  // Add class to selected rows
  if (cellProperties.instance.getDataAtRowProp(row, "0")) {
    Handsontable.dom.addClass(parentElement, SELECTED_CLASS);
  } else {
    Handsontable.dom.removeClass(parentElement, SELECTED_CLASS);
  }

  // Add class to odd TRs
  if (row % 2 === 0) {
    Handsontable.dom.addClass(parentElement, ODD_ROW_CLASS);
  } else {
    Handsontable.dom.removeClass(parentElement, ODD_ROW_CLASS);
  }
};


export const drawCheckboxInRowHeaders = function drawCheckboxInRowHeaders(
  row,
  TH
) {
  const input = document.createElement("input");

  input.type = "checkbox";

  if (row >= 0 && this.getDataAtRowProp(row, "0")) {
    input.checked = true;
  }

  Handsontable.dom.empty(TH);

  TH.appendChild(input);
};

export function alignHeaders(
  _this,
  column,
  TH
) {
  if (column < 0) {
    return;
  }

  if (TH.firstChild) {
    const alignmentClass = _this.isRtl() ? "htRight" : "htLeft";

    if (headerAlignments.has(column.toString())) {
      Handsontable.dom.removeClass(
        TH.firstChild,
        alignmentClass
      );
      Handsontable.dom.addClass(
        TH.firstChild,
        headerAlignments.get(column.toString())
      );
    } else {
      Handsontable.dom.addClass(TH.firstChild, alignmentClass);
    }
  }
}

export const changeCheckboxCell = function changeCheckboxCell(
  event,
  coords
) {
  const target = event.target;

  if (coords.col === -1 && event.target && target.nodeName === "INPUT") {
    event.preventDefault(); // Handsontable will render checked/unchecked checkbox by it own.

    this.setDataAtRowProp(coords.row, "0", !target.checked);
  }
};





const COLUMNS_HEAD = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
const SPLIT_DIVIDER = "_";

const getExcelColumnsHeaders = ( length ) => {

	const multipleNum = Math.ceil(length / 26);
	let ret = '';
	let prefix = '';
	for(let i=0; i< multipleNum; i++){
		ret += COLUMNS_HEAD.replace(/([A-Z])/g,function(...args){
			return ','+prefix+args[0] 
		})
		prefix = COLUMNS_HEAD.charAt(i % 26); 
	}

	return ret.substr(1).split(',');
}

/*
const getExcelCells = ( sheet ) => {
	
	const ret = {
		cells:[],
		mergeCells:[]
	};

	const {fieldList,cellCount,startRow,startCell} = sheet;
	const maxRows = Math.max.apply(null,fieldList.map(field=>field.cellName.split(SPLIT_DIVIDER).length)) ;
	const maxCells = cellCount ;

	for(let r=0;r<startRow;r++){
		const row = [];
		for(let c=0;c<maxCells + startCell;c++){
			
			let cell = "";

			if(r >= (startRow - maxRows) && c >= startCell){

				const rowIndex = r - (startRow - maxRows);
				const colIndex = c - startCell;

				const field = fieldList.find(field =>field.cellNum === colIndex);

				if(field){

					const names = field.cellName.split(SPLIT_DIVIDER);

					if(names.length > 1){

						if(rowIndex < maxRows -1 ){

							let merge = ret.mergeCells.find(m=>m.id === names[rowIndex]);

							if(!merge){
								merge = {
									id:names[rowIndex],
									row:r,
									col:c,
									rowspan:!names[rowIndex+1] ? (maxRows - names.length) + 1 : 1,
									colspan:1
								}
								ret.mergeCells.push(merge)
							}else{
								merge.colspan = c - merge.col + 1;
							}

						}

						if(names[rowIndex]){
							cell = names[rowIndex]
						}

						
					}else{

						if( rowIndex === maxRows -1){
							cell = `${field.cellName}`
						}
						
					}

				}
			}
			row.push(cell)
		}
		ret.cells.push(row)
	}

	ret.mergeCells.forEach(m=>{
		delete m.id
	})

	return ret;
}


export const excelConvert = (source) => {

	const colHeaders = getExcelColumnsHeaders(source.cellCount + source.startCell)

	const columns = colHeaders.map((column,index)=>{
		const isEmpty = index > source.cellCount + source.startCell - 1 ;
		return {
			data:index,
			className:  "htMiddle "+ (isEmpty ? 'empty' : 'base'),
			type:'text',
			readOnly:true
		}
	})

	const {cells,mergeCells} = getExcelCells(source);

	return {colHeaders,mergeCells,columns,cells}
}
*/





const cellsToFields = ( cells ,fileId) => {

	const columns = [];
	const otherColumns = [];

	for(let row = cells.length -1; row >=0 ; row --){
		for(let col = 0 , len = cells[row]?.length??0; col <len ; col ++){

			const cell = cells[row][col];
			if(typeof columns[col] === 'undefined' && typeof cell !== 'undefined'){
				columns[col] = {
					"cellName": cell.trim(),
          "cellNum": col,
          "fileId": fileId,
          "rowNum": row,
          "status": 1,
				}
			}else{
				if(typeof cell !== 'undefined'){
					otherColumns.push({
						"cellName": cell.trim(),
	          "cellNum": col,
	          "fileId": fileId,
	          "rowNum": row,
	          "status": 0,
					})
				}
				
			}
		}
	}

	let startCell = 0;

	for(let i=0,len = columns.length; i<len; i++){
		if(typeof columns?.[i]?.cellName === 'undefined'){
			startCell ++
		}
		if(typeof columns?.[i+1]?.cellName === 'undefined'){
			break;
		}
	}
	
	return {
		startCell,
		fieldList:[...columns.slice(startCell),...otherColumns]
	}
}
export const exportXLSX = (sheets,fileName) => {

	const wb = XLSX.utils.book_new();

	sheets.map(sheet=>{
		const ws = XLSX.utils.aoa_to_sheet(sheet.cells, {});
		ws['!merges'] = sheet.mergeCells.map(m=>{
			return {
				s:{
					r:m.row,
					c:m.col
				},
				e:{
					r:m.row + m.rowspan -1,
					c:m.col + m.colspan -1
				}
			}
		});

		Object.keys(ws).forEach(k=>{

			const m = k.match(/^[A-Z]+[0-9]+$/);

			if(m){
				ws[k].s = {
          font: {
            name: "宋体",
            sz: 12,
            color: {
              auto: 1,
            },
          },
          alignment: {
            /// 自动换行
            wrapText: 1,
            // 居中
            horizontal: "center",
            vertical: "center",
            indent: 0,
          },
        };
			}
		})
     XLSX.utils.book_append_sheet(wb, ws, sheet.sheetName);
	})

  return XLSX.writeFile(wb, fileName);
}
export const listConvertToExcelData = ( list , fieldList) => {
	const cells = [];
	(list??[]).forEach((item,index)=>{
		
		const rowCells = []

		
		Object.keys(item).forEach(entityField=>{
			const field = (fieldList??[]).find(_field=>_field.entityField === entityField);
			if(field){
				console.log(field)
				if(['序号','No','NO'].indexOf(field.cellName) > -1){
					rowCells[field.cellNum] = index +1;
				}else{
					rowCells[field.cellNum] = item[entityField];
				}
				
			}
			
		})
		cells.push(rowCells)
	})
	return cells
}
export const excelConvertToServerData = ( sheets , fileId ) => {
	return (sheets??[]).map( (sheet,index) =>{
		const {startCell,fieldList} = cellsToFields(sheet.cells,fileId)
		return {
			fileId,
			//sheetId:sheet.sheetId ?? uuid(),
			sheetName:sheet.sheetName,
			fieldList: fieldList,
			cellCount: sheet.totalCols - startCell, //有效列数
			startRow: sheet.totalRows ,
			startCell: startCell,
			orderNum: index,
			remark:JSON.stringify(sheet.mergeCells)
		}
	})
}
export const serverDataconvertToExcel = ( sheets  ) => {

	return (sheets??[]).map( (sheet,index) =>{
		
		const colHeaders = getExcelColumnsHeaders( sheet.cellCount + sheet.startCell)
		let mergeCells = [];
		try{
			mergeCells = JSON.parse(sheet.remark??'[]');
		}catch(e){

		}

		const cells = [];

		sheet.fieldList.forEach(field=>{
			//field.cellNum
			//field.rowNum;

			if(!cells[field.rowNum]){
				cells[field.rowNum] = []
			}
			cells[field.rowNum][field.cellNum] = field.cellName
		})


		const columns = colHeaders.map((column,index)=>{
			const isEmpty = index > sheet.cellCount + sheet.startCell - 1 ;
			return {
				data:index,
				className:  "htMiddle "+ (isEmpty ? 'empty' : 'base'),
				type:'text',
				readOnly:true
			}
		})
		return {
			colHeaders,
			mergeCells,
			columns,
			sheetId:sheet.sheetId ?? uuid(),
			sheetName:sheet.sheetName,
			cells:cells ,
			totalCols: sheet.cellCount + sheet.startCell,
			totalRows: sheet.startRow ,
		}
	})
}

export const readExcel = ( file ) => {
    const fileReader = new FileReader()
    fileReader.readAsBinaryString(file)
    return new Promise(function(resolve, reject) {

        fileReader.onload = function ( event ) {
					try {
						const data = event.target.result;
						const workbook = XLSX.read(data, {
							type: 'binary'
						}) 

						const sheets = [];

						const headers = getExcelColumnsHeaders(100);


						
						workbook.Workbook.Sheets.filter(w=>w.Hidden === 0).forEach(work => {
							const worksheet = workbook.Sheets[work.name];
							const sheetDataKeys = Object.keys(worksheet);
							console.log(worksheet)
							const cellCount = Math.max.apply(null, sheetDataKeys.filter(w=>/^[A-Z]+[0-9]+$/.test(w)).map(w=>{
								const m = w.match(/^([A-Z]+)/);
								return headers.indexOf(m[1]) + 1 //找到列标识A B C D来定位最大值 
							}));

							const colHeaders = getExcelColumnsHeaders(cellCount);
							const columns = colHeaders.map((column,index)=>{
								const isEmpty = index > cellCount -1;
								return {
									data:index,
									className: "htMiddle "+(isEmpty ? 'empty' : 'base'),
									type:'text',
									readOnly:true
								}
							})

							

							const sheet = {
								sheetName:work.name,
								sheetId:uuid(),
								cells:[],
								mergeCells:[],
								colHeaders,
								columns,
							};

							sheetDataKeys.forEach(key=>{
								const value = key.charAt(0) === '!' ? worksheet[key] : worksheet[key].v;

								const m = key.match(/^([A-Z]+)([0-9]+)$/);

								if(m && m[1] && m[2]){
									const colIndex = headers.indexOf(m[1]);
									const rowIndex = m[2] - 1

									if(!Array.isArray(sheet.cells[rowIndex])) {
										sheet.cells[rowIndex] = []
									}
									sheet.cells[rowIndex][colIndex] = worksheet[key].w;
								}


								
							})

							sheet.totalCols = cellCount;
							sheet.totalRows = sheet.cells.length;
							sheet.mergeCells = worksheet['!merges'].map(merge=>{
								return {row:merge.s.r,col:merge.s.c,   rowspan:merge.e.r - merge.s.r + 1,colspan:merge.e.c - merge.s.c + 1}
							})
							
							sheets.push(sheet)
						});
						resolve(sheets)
					} catch (e) {
					reject(e)
					}
        }
    })
}


export const uuid = () => {
  let d = new Date().getTime();
  
  const uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
      var r = (d + Math.random() * 16) % 16 | 0;
      d = Math.floor(d / 16);
      return (c == 'x' ? r : (r & 0x3 | 0x8)).toString(16);
  });
  return uuid
}