import React, {
	useState,useCallback,useRef,useMemo,useEffect 
} from 'react';
import styled from 'styled-components/macro';
import {DialogForm} from 'app/components/DialogForm';
import {
  ProForm,
  ProFormSelect,
  ProFormText,
  ProFormDigit,
  ProFormTreeSelect
} from '@ant-design/pro-components';

import {message} from 'antd';



import { useHistory } from 'react-router';

import { useDispatch, useSelector } from 'react-redux';
import { useReportSlice } from './slice';
import { selectOrgId } from '../../slice/selectors';
import {
  selectSaveReportLoading,
} from './slice/selectors';
import {
  saveReport
} from './slice/thunks';




const renderForm = ( {} ) => () => {

	return <FormWrap>
		<ProFormText 
			width="lg"
			name="name" 
			fieldProps={{size:'small'}}
			label={ '报表名称' }
			placeholder={'报表名称'}
			rules={[{ required: true, message: `请输入报表名称` }]}
		/>
	</FormWrap>
}

interface PostProps{
	data:Category;
	children:React.ReactNode;
	title?:string;
	onSuccess?:()=>void;
}

export const Post: React.FC = ({ children,title, data , onSuccess }) => {

	const dispatch = useDispatch();
	const orgId = useSelector(selectOrgId);
 	const ref = useRef();
 	const loading = useSelector(selectSaveReportLoading);

 	const [formData,setFormData] = useState(data);


 	const onShow = () =>{
 		if(!data?.id){
 			
 			dispatch(saveReport({
        report: {},
        resolve: (result) => {
        	console.log(result)
        	setFormData({...data!,...result})
        },
      }))
 		}else{
 			setFormData(data)
 		}
 	}
 	const onFinish = ( values ) => {
    dispatch(
      saveReport({
        report: {...formData!, ...values },
        resolve: () => {
        	onSuccess()
        	ref.current.hide()
          message.success('保存成功');
        },
      }),
    );
 	};

  return <DialogForm
  	
		loading={loading}
		loadingText="保存中..."
		render={renderForm({})} 
		initialValues={ formData }
		onFinish={onFinish} 
    title={title}
    onShow={onShow}
    ref={ref}
   >
		{children}
	</DialogForm>
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