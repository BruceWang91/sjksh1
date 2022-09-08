import React, {
	useState,useCallback,useRef,useMemo,useEffect
} from 'react';
import styled from 'styled-components/macro';
import {DialogForm} from 'app/components/DialogForm';
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




interface PostProps{
	data:Filemain;
	children:React.ReactNode;
	title?:string;
	onSuccess?:()=>void;
}

export const UpdateInfo: React.FC = ({ children  , title, data , onSuccess }) => {
	useFilemainSlice();


	const ref = useRef();
 	

	const loading = useSelector(selectSaveFilemainLoading);


	const dispatch = useDispatch();
  const orgId = useSelector(selectOrgId);


	

	const onFinish = ( values ) => {
 		dispatch(
      editFilemain({
        filemain: {...data, ...values },
        resolve: () => {
        	onSuccess()
        	ref.current.hide()
          message.success('保存成功');
        },
      }),
    );
 	};

	const formElements = <>
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
				name="classId" 
				fieldProps={{size:'small',style:{minWidth:200}}}
				label={  "分类" }
				placeholder={"请选择分类"}
				rules={[{ required: true, message: `请选择分类` }]}
				request={()=> {
					return new Promise((resolve,reject)=>{
						dispatch(
							getCategories({orgId,parentId:0,resolve:data=>{
							resolve(data)
							}})
						)
					})
				}}
			/>
      <ProFormText 
        fieldProps={{size:"small"}} 
        width="md" name="orderNum" 
        label="显示顺序" 
        placeholder="请输入顺序" 
        rules={[{ required: true, message: `请输入顺序` }]}
      />
    
    </ProForm.Group>	
  	<ProFormTextArea  
  		fieldProps={{size:"small"}} 
  		name="remark" 
  		label="备注 " 
  		placeholder="请输入备注" />
  </>
	return <DialogForm
  
		loading={loading}
		loadingText="保存中..."
		render={formElements} 
		initialValues={ data }
		onFinish={onFinish} 
    title={title}
    ref={ref}
   >
		{children}
	</DialogForm>

};


UpdateInfo.defaultProps = {
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