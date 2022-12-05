import React, {
	useState,useCallback,useRef,useMemo,useEffect
} from 'react';
import styled from 'styled-components/macro';
import {Dialog} from 'app/components/Dialog';
import {File} from 'app/components/File';
import {readExcel,serverDataconvertToExcel ,excelConvertToServerData,uuid,exportXLSX} from '../utils';
import type { ProFormInstance,ProColumns } from '@ant-design/pro-components';
import {
  ProFormDateRangePicker,
  ProFormSelect,
  ProFormText,
  ProFormTextArea,
  ProForm,
  ProFormTreeSelect,
} from '@ant-design/pro-components';

import { Button, message, Steps ,Tabs} from 'antd';
import { Excel } from '../Excel';


import { useDispatch, useSelector } from 'react-redux';
import { useFilemainSlice } from './slice';

import { selectOrgId } from '../../../slice/selectors';
import {

  selectSaveFilemainLoading,
  selectSources,
} from './slice/selectors';
import {
  importFilemain,
  getFilemains2,
  getSources,
} from './slice/thunks';
import {
  Filemain,
  FilemainViewModel,
} from './slice/types';


import {selectCategories} from '../CategoryManager/slice/selectors';
import { useCategorySlice } from '../CategoryManager/slice';

 import {getCategories} from '../CategoryManager/slice/thunks';
import { listToTree } from 'utils/utils';
import treeNodeIconFn from 'utils/treeNodeIconFn'
import {selectFolder} from 'app/pages/MainPage/Sider/slice/selectors';
import {ResourceTypes} from 'app/pages/MainPage/pages/PermissionPage/constants';
import {EXCEL_ACCEPT} from 'globalConstants'
const { Step } = Steps;
const { TabPane } = Tabs;


interface PostProps{
	data:Filemain;
	children:React.ReactNode;
	title?:string;
	onSuccess?:()=>void;
}

export const Post: React.FC = ({ children  , title, data , parentId, onSuccess }) => {
	useFilemainSlice();
	useCategorySlice();
	const ref = useRef();
	const loading = useSelector(selectSaveFilemainLoading);



	const dispatch = useDispatch();
  const orgId = useSelector(selectOrgId);


	const [step,setStep] = useState(0);
	const [sheets,setSheets] = useState([]);
	const [fileName,setFileName ] = useState(null);
	const formRef = useRef();


	const folderMap = useSelector(selectFolder);
 	const categories = useSelector(selectCategories);
 	const sources = useSelector(selectSources);

 	const folders = useMemo(()=>[{
		title:'根目录',
		path:[],
		key:'',
		isFolder:1,
		icon:treeNodeIconFn,

		value:'',
		id:'',
		children:listToTree(
      folderMap[ResourceTypes.ExcelTemplate]?.list,
      null,
      [],
      {getIcon: () => treeNodeIconFn}
  	)
	}],[folderMap])

	const onSelectFile = (file) => {

		readExcel(file).then(result=>{

			setSheets(result)
		})
		setFileName(file.name)
	}


	const onOpen = useCallback(()=>{

 		dispatch(getCategories({parentId:0,orgId}))
 		dispatch(getSources({orgId}))
 	},[dispatch,orgId])



	const contents = [
		<ContenetWrap>
			<div>
				{sheets.length > 0 && (<Tabs type="card" >
	        {sheets.map((sheet,key) => {
	        	const excelProps = {
	        		columns:sheet.columns,
	        		colHeaders:sheet.colHeaders.map((c,i)=>sheet?.flags?.[i] === 1 ? `${c} ✲`:c),
	        		cellsOption:sheet.cellsOption,
	        		mergeCells:sheet.mergeCells,
	        		cells:sheet.cells,
	        		viewPortWidth:'auto',
	        		viewPortHeight:'auto',


	        		customBorders: typeof sheet.validEndRow === 'number'  ? [
                {
                    range: {
                        from: {
                            row: sheet.validEndRow,
                            col: 0
                        },
                        to: {
                            row: sheet.validEndRow,
                            col: sheet.columns.length
                        }
                    },
                    top: {
                        width: 2,
                        color: 'orange'
                    },
                    left: {
                        width: 2,
                        color: 'orange'
                    },
                    bottom: {
                        width: 2,
                        color: 'orange'
                    },
                    right: {
                        width: 2,
                        color: 'orange'
                    }
              }] : null,

	        		afterOnCellMouseDown: function(event,cellCoords){
	        			
	        			if(cellCoords.col === -1){

	        				sheet.validEndRow =  cellCoords.row;
	        				setSheets([...sheets])
	        			
	        			}

	        			
	        		},
	        		dropdownMenu:{
				      	callback: function (key, options) {
				         

				           if(!sheet.flags){
				           		sheet.flags = {}
				           }

				           let flag = sheet.flags[options?.[0]?.start?.col] || 0;
				           
				           sheet.flags[options?.[0]?.start?.col] = Math.abs(flag - 1)

				           setSheets([...sheets])
				        },
				      	items:{
				      		flag: {
				      			name:function(){
				      				const range = this.getSelected();
				      				const col = range?.[0]?.[1];
				      				const isFlag = sheet?.flags?.[col] === 1;
				      				return isFlag ? '设定此列为必填 √' : '设定此列为必填'
				      			}
				      		}
				      		
				      	},
				      }
	        	}

	        	if(sheet.cells.length === 0){
	        		excelProps.cells = [['']]
	        	}
	        	return (
		          <TabPane tab={sheet.sheetName} key={key}>
		            
		           <Excel {...excelProps} />
		          </TabPane>
		        )
	        })}
	      </Tabs>)}
			</div>
			<div className="upload"><File onChange={onSelectFile}  accept={EXCEL_ACCEPT} /></div>
			<Descp>
	    	<h5>注意事项</h5>
	    	<p>1、请选择规范的表样excel文件；</p>
				<p>2、表样excel文件不能包含数据记录；</p>
	    </Descp>
		</ContenetWrap>,


		<ContenetWrap>
			<div className="form">
			<ProForm<{
      fileName: string;
      orderNum: string;
      remark:string;
	    }>
	    	initialValues={{...data,fileName:data.fileName || fileName,orderNum:data.orderNum || 0 , parentId:data?.parentId || parentId || null }}
	    	formRef={formRef}
	      onFinish={async (values) => {
	       	dispatch(
	          importFilemain({
	            filemain: { ...values,fileId:data.fileId, orgId,parentId:values.parentId || parentId, sheets:excelConvertToServerData(sheets,data.fileId)},
	            resolve: () => {
	              message.success('提交成功');
	              ref.current.hide();
	              onSuccess()
	            },
	          }),
	        );
	        
	      }}
	     

		    submitter={{
	        render: (props, doms) => {
	          return [
	           
	          ];
	        },
	      }}
	    >
	      <ProForm.Group>
	        <ProFormText
	          width="md"
	          name="fileName"
	          label="文件名"
	          placeholder="请输入文件名"
	          rules={[{ required: true, message: `请输入文件名` }]}
	          fieldProps={{size:"small"}}
	        />
	        <ProFormTreeSelect
						name="parentId" 
						fieldProps={{treeIcon:true,size:'small',style:{minWidth:200}}}
						label={  "目录" }
						placeholder={"根目录"}
						rules={[{ required: false, message: `请选择目录` }]}
						request={()=>folders}
					/>
					<ProFormTreeSelect
						name="classId" 
						fieldProps={{size:'small',style:{minWidth:200}}}
						label={  "标签" }
						placeholder={"请选择标签"}
						rules={[{ required: true, message: `请选择标签` }]}
						request={()=>categories}
					/>
	        <ProFormText 
		        fieldProps={{size:"small"}} 
		        width="md" name="orderNum" 
		        label="显示顺序" 
		        placeholder="请输入顺序" 
		        rules={[{ required: true, message: `请输入顺序` }]}
	        />
	      
	      </ProForm.Group>
	      <ProFormSelect
						name="sourceId" 
						fieldProps={{size:'small',style:{minWidth:400}}}
						label={  "数据源" }
						placeholder={"请选择数据源"}
						rules={[{ required: true, message: `请选择数据源` }]}
						request={()=>sources.map(d=>({label:d.name,value:d.id}))}
				/>
	      <ProFormTextArea  fieldProps={{size:"small"}} width="md" name="remark" label="备注 " placeholder="请输入备注" />      
	    </ProForm>
	    </div>
		</ContenetWrap>];
	


	const next = () => {
 		setStep(step+1)
 	}
 	const prev = () => {
 		setStep(step-1)
 	}

  return <Dialog
  	width="80%"
		loading={loading}
		loadingText="保存中..."
		onOpen={onOpen}
		render={
			<FormWrap>
				<Steps current={step} size="small">
		      <Step key="0" title="上传表样" />
		      <Step key="1" title="文件信息" />
		    </Steps>
		    <div className="steps-content">
		    	<div>{contents[step]}</div>
		    </div>
		    <div className="steps-action">
		      {step < 2 - 1 && (
		        <Button size="small" type="primary" disabled={sheets.length === 0} onClick={() => next()}>
		          下一步
		        </Button>
		      )}
		      {step === 2 - 1 && (
		        <Button size="small" type="primary" onClick={() =>formRef.current.submit()}>
		          完成
		        </Button>
		      )}
		      {step > 0 && (
		        <Button size="small" style={{ margin: '0 8px' }} onClick={() => prev()}>
		          上一步
		        </Button>
		      )}
		    </div>
			</FormWrap>
		} 
    title={title}
    footer={false}
    ref={ref}
    destroyOnClose
    
   >
		{children}
	</Dialog>
};


Post.defaultProps = {
	onSuccess:function(){},
	data:{},
}
const FormWrap = styled.div`
	

.ant-form-item-explain-error{
	font-size:12px;
	color:${p => p.theme.error};
}
	
.steps-action{
	text-align:center;
}

`
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
	
	
	
	margin:12px 0;

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

	 .form{
	 	padding:16px;
	 }
`