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
  selectDeleteFilemainsLoading,
  selectSaveFilemainLoading,
  selectFilemainListLoading,
  selectFilemains,
} from './slice/selectors';
import {
  deleteFilemain,
  editFilemain,
  getFilemains,
  importFilemain
} from './slice/thunks';
import {
  Filemain,
  FilemainViewModel,
} from './slice/types';



import {
  getCategories,
} from '../CategoryManager/slice/thunks';




const { Step } = Steps;
const { TabPane } = Tabs;


interface PostProps{
	data:Filemain;
	children:React.ReactNode;
	title?:string;
	onSuccess?:()=>void;
}

export const Post: React.FC = ({ children  , title, data , onSuccess }) => {
	useFilemainSlice();
	const ref = useRef();
 
	const loading = useSelector(selectSaveFilemainLoading);



	const dispatch = useDispatch();
  const orgId = useSelector(selectOrgId);


	const [step,setStep] = useState(0);
	const [sheets,setSheets] = useState([]);
	const [fileName,setFileName ] = useState(null);
	const formRef = useRef();

	const onSelectFile = (file) => {
		readExcel(file).then(result=>{
			setSheets(result)
		})
		setFileName(file.name)
	}


	const contents = [
		<ContenetWrap>
			<div>
				{sheets.length > 0 && (<Tabs type="card" >
	        {sheets.map((sheet,key) => (
	          <TabPane tab={sheet.sheetName} key={key}>
	            <Excel 
	            	columns={sheet.columns} 
								colHeaders={sheet.colHeaders} 
								mergeCells={sheet.mergeCells} 
								cellsOption={sheet.cellsOption} 
								cells={sheet.cells}
								viewPortWidth={'auto'}
								viewPortHeight={'auto'}
								 />
	          </TabPane>
	        ))}
	      </Tabs>)}
			</div>
			<div className="upload"><File onChange={onSelectFile}  accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" /></div>
			<Descp>
	    	<h5>????????????</h5>
	    	<p>1???????????????????????????excel?????????</p>
				<p>2?????????excel?????????????????????????????????</p>
	    </Descp>
		</ContenetWrap>,


		<ContenetWrap>
			<div className="form">
			<ProForm<{
      fileName: string;
      orderNum: string;
      remark:string;
	    }>
	    	initialValues={{...data,fileName:data.fileName || fileName,orderNum:data.orderNum || 0 }}
	    	formRef={formRef}
	      onFinish={async (values) => {
	       	dispatch(
	          importFilemain({
	            filemain: { ...values,fileId:data.fileId, orgId, sheets:excelConvertToServerData(sheets,data.fileId)},
	            resolve: () => {
	              message.success('????????????');
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
	          label="?????????"
	          placeholder="??????????????????"
	          rules={[{ required: true, message: `??????????????????` }]}
	          fieldProps={{size:"small"}}
	        />

	       <ProFormTreeSelect
						name="classId" 
						fieldProps={{size:'small',style:{minWidth:200}}}
						label={  "??????" }
						placeholder={"???????????????"}
						rules={[{ required: true, message: `???????????????` }]}
						request={()=>new Promise((resolve,reject)=>{
							dispatch(
								getCategories({orgId,parentId:0,resolve:data=>{
								resolve(data)
								}})
							)
						})}
					/>
	        <ProFormText 
		        fieldProps={{size:"small"}} 
		        width="md" name="orderNum" 
		        label="????????????" 
		        placeholder="???????????????" 
		        rules={[{ required: true, message: `???????????????` }]}
	        />
	      
	      </ProForm.Group>	
	      <ProFormTextArea  fieldProps={{size:"small"}} width="md" name="remark" label="?????? " placeholder="???????????????" />      
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
		loadingText="?????????..."
		render={
			<FormWrap>
				<Steps current={step} size="small">
		      <Step key="0" title="????????????" />
		      <Step key="1" title="????????????" />
		    </Steps>
		    <div className="steps-content">
		    	<div>{contents[step]}</div>
		    </div>
		    <div className="steps-action">
		      {step < 2 - 1 && (
		        <Button size="small" type="primary" disabled={sheets.length === 0} onClick={() => next()}>
		          ?????????
		        </Button>
		      )}
		      {step === 2 - 1 && (
		        <Button size="small" type="primary" onClick={() =>formRef.current.submit()}>
		          ??????
		        </Button>
		      )}
		      {step > 0 && (
		        <Button size="small" style={{ margin: '0 8px' }} onClick={() => prev()}>
		          ?????????
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
	
.ant-form-item-label{
	padding:4px 4px 0 0 !important
}
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