import React , {useMemo} from 'react';
import 'handsontable/dist/handsontable.full.css';
import { HotTable } from '@handsontable/react';
import styled from 'styled-components/macro';

export interface ExcelIProps {
	cells:any[];
	columns:any[];
	colHeaders:any[];
	mergeCells:any[];
	cellsOption:any[];
	viewPortHeight?:number;
	viewPortWidth?:number;
	dropdownMenu:any;
	contextMenu:any
}


export function Excel({
	cells,
	columns,
	colHeaders,
	mergeCells,
	cellsOption,
	viewPortHeight,
	viewPortWidth,
	dropdownMenu,
	contextMenu,
	...rest
}:IProps) {

	const config = useMemo(()=>{

		let ret = {
			...rest,
      dropdownMenu: dropdownMenu,
      hiddenColumns: {
        indicators: true,
      },
      contextMenu: contextMenu,
      multiColumnSorting: false,
      manualColumnResize:true,

			manualRowResize:true,
      filters: false,
      rowHeaders: true,
      autoColumnSize:true,
     	
      colHeaders:[],
      columns: [],
      licenseKey: "non-commercial-and-evaluation",
		};

  	ret.columns = columns;
  	ret.colHeaders = colHeaders ?? [];
  	ret.mergeCells = mergeCells ?? [];

  	if(typeof viewPortHeight !== 'undefined'){
  		ret.height = viewPortHeight;
  	}
  	if(typeof viewPortWidth !== 'undefined' ){
  		ret.width = viewPortWidth ;
  	}

  	ret.cell = cellsOption ;

  	return ret;

	},[
    columns,
    colHeaders,
    mergeCells,
    cellsOption,
    viewPortHeight,
    viewPortHeight
])


	return <Wrap><HotTable {...config} data={cells}   /></Wrap>
}








const Wrap = styled.div`
	
	
	
	.handsontable {
	  font-size: 13px;
	  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Roboto', 'Oxygen',
	  'Ubuntu', 'Helvetica Neue', Arial, sans-serif;
	  font-weight: 400;

	  .htRight .changeType {
	    margin: 3px 1px 0 13px;
	  }

	  .collapsibleIndicator {
	    text-align: center;
	  }

	  
	}
`