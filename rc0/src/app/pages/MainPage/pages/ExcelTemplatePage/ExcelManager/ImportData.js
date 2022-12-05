import React, {
	useState,useCallback,useRef,useMemo,useEffect
} from 'react';
import styled from 'styled-components/macro';
import {Dialog} from 'app/components/Dialog';
import {File} from 'app/components/File';
import {readExcel,serverDataconvertToExcel ,excelConvertToServerData,uuid,exportXLSX} from '../utils';
import { ProDescriptions} from '@ant-design/pro-components';
import Icon, {InfoCircleOutlined,InsertRowBelowOutlined,InsertRowAboveOutlined } from '@ant-design/icons';

import {
  ProFormDateRangePicker,
  ProFormSelect,
  ProFormText,
  ProFormTextArea,
  ProForm,
  ProFormTreeSelect,
} from '@ant-design/pro-components';

import { Button, message ,Popover ,Tabs} from 'antd';
import { Excel } from '../Excel';


import { useDispatch, useSelector } from 'react-redux';
import { useFilemainSlice } from './slice';
import { selectOrgId } from '../../../slice/selectors';
import {
  selectDeleteFilemainsLoading,
  selectSaveFilemainLoading,
  selectFilemainListLoading,
  selectFilemains,
  selectFilemainLoading,
  selectImportDataLoading
} from './slice/selectors';
import {
  deleteFilemain,
  editFilemain,
  getFilemains,
  importFilemain,
  getFilemain,
  importFilemainData
} from './slice/thunks';
import {
  Filemain,
  FilemainViewModel,
} from './slice/types';



import {
  getCategories,
} from '../CategoryManager/slice/thunks';




const { TabPane } = Tabs;


interface PostProps{
	data:Filemain;
	children:React.ReactNode;
	title?:string;
	onSuccess?:()=>void;
}

export const ImportData: React.FC = ({ children  , title, data , onSuccess }) => {
	useFilemainSlice();
	const ref = useRef();
 	const actionRef = useRef();

	const loading = useSelector(selectSaveFilemainLoading);
	const tempLoading = useSelector(selectImportDataLoading);



	const dispatch = useDispatch();
  const orgId = useSelector(selectOrgId);


	const [sheets,setSheets] = useState([]);
	const [selectfile,setSelectFile ] = useState(null);
	const [importSheets,setImportSheets] = useState([]);

	const onSelectFile = (file) => {
		readExcel(file).then( _sheets=>{
			setImportSheets(_sheets)
		})
		setSelectFile(file)
	}


	const onOpen = () => {
		dispatch(getFilemain({fileId:data.fileId,resolve:result=>{
			setSheets(serverDataconvertToExcel(result.sheets)  );
		}})) 
	}
	
	const result = useMemo(()=>{

		const checkResult = {
  		errorNum:0,
  		tempSheetNum:0,
  		sheetNum:0,
  		message:[]
  	};
		//表样记录数
  	checkResult.tempSheetNum = sheets.length || 0;
  	checkResult.sheetNum = importSheets?.length || 0;

  	if(checkResult.tempSheetNum !== checkResult.sheetNum){
  		checkResult.message.push('与表样工作簿数量不一致!');
  		checkResult.errorNum ++;
  	}

	  const styleSheets = sheets.map((tempSheet,index)=>{
  		const sheet = importSheets[index] || {
  			sheetId:uuid(),
  			cells:[],
  			mergeCells:[],
  			columns:[],
  			colHeaders:[],
  			totalCols:0,
  			totalRows:0,
  		}; 

  		if(sheet.totalCols < tempSheet.totalCols){
  			checkResult.errorNum ++;
  			checkResult.message.push(`表~${sheet.sheetName}列数(${sheet.totalCols})小于对应表样列数(${tempSheet.totalCols})!`);
  		}

  		sheet.cellsOption = [];
  		tempSheet.cellsOption = [];
  		for(let row=0 , rows = tempSheet.cells.length; row < rows; row++){
  			const cells = []
  			for(let col=0 , cols = sheet.totalCols; col < cols; col++){

  				const a1 = String(sheet.cells?.[row]?.[col]).trim() ;
  				const a2 =  String(tempSheet.cells?.[row]?.[col]).trim();
  				//console.log(a1,a2,a1 === a2 )
  				if( a1 ===  a2  || a2 === '' || a2 === 'undefined' ){
  					sheet.cellsOption.push({
  						row,
  						col,
  						className:'htMiddle htCenter success'
  					})
  				}else{
  					let msg = `表~${sheet.sheetName}-[${sheet.colHeaders[col]}${row}]与对应表样单元格数据不匹配!`
  					checkResult.errorNum ++;
		    		checkResult.message.push(msg);
  					sheet.cellsOption.push({
  						row,
  						col,
  						className:'htMiddle htCenter error excel-match-error '+msg
  					})

  					tempSheet.cellsOption.push({
  						row,
  						col,
  						className:'htMiddle htCenter tip'
  					})
  				}
  			}
  		}
  		/*const columns = sheet.colHeaders.map((column,index)=>{
					const isEmpty = index > sheet.totalCols  - 1 ;
					return {
						data:index,
						className:  "htMiddle "+ (isEmpty ? 'empty' : 'success'),
						type:'text',
						readOnly:true
					}
				})

  		sheet.columns = columns*/
  		return {
  			import:sheet,
  			temp:tempSheet
  		}
  	})
  	

  	if(checkResult.sheetNum === 0){
  		checkResult.errorNum = 0;
  		checkResult.message = []
  	}
  return {
  	checkResult,
  	styleSheets
  }
	},[importSheets,sheets])
	





	const uploadHandler = () => {

		dispatch(importFilemainData({
			file:selectfile,
			fileId:data.fileId,
			resolve:()=>{
				onSuccess && onSuccess()
				message.success('数据导入成功!');
				ref.current.hide();
			}
		}))
	}

	useEffect(()=>{
		actionRef.current && actionRef.current.reload()
	},[result,actionRef])
  return <Dialog
  	width="80%"
  	style={{ top: 30 }}

		loading={tempLoading||loading}
		loadingText=""
		render={
			<ContenetWrap>
				<div>

					{result.styleSheets.length > 0 && (<Tabs type="card" >
		        {result.styleSheets.map((sheet,key) => (
		          <TabPane tab={sheet.temp.sheetName} style={{width:'100%'}} key={key}>
		            <Excel 

		            	columns={sheet.temp.columns} 
									colHeaders={sheet.temp.colHeaders} 
									mergeCells={sheet.temp.mergeCells} 
									cellsOption={sheet.temp.cellsOption} 
									cells={sheet.temp.cells}
									viewPortWidth={'auto'}
									viewPortHeight={130}
									 />
								<div className="arrow-title"><InsertRowBelowOutlined /> 表上为表样， <InsertRowAboveOutlined /> 表下为当前导入数据</div>
								<Excel 
		            	columns={sheet.temp.columns} 
									colHeaders={sheet.import.colHeaders} 
									mergeCells={sheet.import.mergeCells} 
									cellsOption={sheet.import.cellsOption} 
									cells={sheet.import.cells.length === 0 ? [['请选择与表样一致的数据表格']] :sheet.import.cells}
									viewPortWidth={'auto'}
									viewPortHeight={200}
									 />
		          </TabPane>))}
		      </Tabs>)}
				</div>
				<div className="upload"><File onChange={onSelectFile}  accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" /></div>
				<Descp>
		    	<ProDescriptions
		    		actionRef={actionRef}
			      title="导入数据与表样对比情况"
			      request={async () => {
			        return Promise.resolve({
			          success: true,
			          data: result.checkResult,
			        });
			      }}
			      columns={[
			        {
			          title: '异常',
			          key: 'errorNum',
			          dataIndex: 'errorNum',
			          render: num => <Popover content={
			          	<div>
			          	{
			          		result.checkResult.message.map((d,i)=><div>{`${i+1}）、${d}`}</div>) 
			          	}
			          	</div>
			          }
			          >
			          	<span><b style={{color:'red'}}>{num}</b> <InfoCircleOutlined /></span>
			          </Popover>

			        },
			        {
			          title: '表样sheet数量',
			          key: 'sheetNum',
			          dataIndex: 'sheetNum',
			        },
			        {
			          title: '导入sheet数量',
			          key: 'tempSheetNum',
			          dataIndex: 'tempSheetNum',
			        },
			      ]}
			    >
			     
			    </ProDescriptions>
		    </Descp>
	

			</ContenetWrap>
		} 
    title={title}
    footer={[
    	<Button key="cancel" onClick={event=>ref.current.hide()} size="small">取消</Button>,
    	<Button key="ok" onClick={event=>uploadHandler()} type="primary" disabled={result.checkResult.errorNum > 0 || tempLoading || loading} size="small">导入</Button>
    	]}
    onOpen={onOpen}
    ref={ref}
    destroyOnClose
   >
		{children}
	</Dialog>
};


ImportData.defaultProps = {
	onSuccess:function(){},
	data:{},
}

const Descp = styled.div`
	

	color:#777;
	background:${p => p.theme.tipBackground};
	padding:16px;
	> p{
		margin-bottom: 2px;
	}
	> p.indent{
		padding-left:16px;
	}
	h5{
		font-size:12px;
	}


`

const ContenetWrap = styled.div`
	
	
	
	margin:0 0;


	color:#777;
	background:${p => p.theme.tipBackground};
	.upload{
		padding:16px;
	}

	.ant-tabs-nav{
		margin-bottom:0;
		height:32px;
	}

 .ant-tabs-tab{
 	padding:0 12px !important;
 }

 .arrow-title{
 	padding:4px 16px;
 	color:red;
 }

 .ant-pro-core-label-tip{
 	display:inline-flex;

 }
 .ant-pro-core-label-tip-title{
 	margin-right:10px;
 }


	.htCore {
	  tr.odd td {
	    background: #fafbff;
	  }

	  tr.selected td {
	    background: #edf3fd;
	  }

	  td.error {
	    background: #ff0000;
	    color: #fcb515;

	  }

	  td.empty {
	    background: #ccc;
	  }
	}
`