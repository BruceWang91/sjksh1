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
  saveReport,
} from './slice/thunks';

import { listToTree } from 'utils/utils';
import treeNodeIconFn from 'utils/treeNodeIconFn'
import {selectFolder} from 'app/pages/MainPage/Sider/slice/selectors';
import {ResourceTypes} from 'app/pages/MainPage/pages/PermissionPage/constants';


interface PostProps{
	children:React.ReactNode;
	title?:string;
	onSuccess?:()=>void;
}

export const Post: React.FC = ({ children,title, parentId,onSuccess }) => {

	const dispatch = useDispatch();
	const orgId = useSelector(selectOrgId);
 	const ref = useRef();
 	const loading = useSelector(selectSaveReportLoading);



 	const onFinish = ( values ) => {
 		
    dispatch(
      saveReport({
        report: { ...values,orgId,isFolder:0,type:'datainfo',parentId:values.parentId || parentId || null },
        resolve: () => {
        	onSuccess()
        	ref.current.hide()
          message.success('保存成功');
        },
      }),
    );
 	};
 	const folderMap = useSelector(selectFolder);

 	const folders = useMemo(()=>[{
		title:'根目录',
		path:[],
		key:'',
		isFolder:1,
		icon:treeNodeIconFn,

		value:'',
		id:'',
		children:listToTree(
      folderMap[ResourceTypes.Report]?.list,
      null,
      [],
      {getIcon: () => treeNodeIconFn}
  	)
	}],[folderMap]);
	
 	

 	const formElements = useMemo(()=>(<FormWrap>
		<ProFormText 
			width="lg"
			name="name" 
			fieldProps={{size:'small'}}
			label={ '报表名称' }
			placeholder={'报表名称'}
			rules={[{ required: true, message: `请输入报表名称` }]}
		/>

		<ProFormTreeSelect
			name="parentId" 
			fieldProps={{treeIcon:true,size:'small',style:{minWidth:200}}}
			label={  "目录" }
			placeholder={"根目录"}
			rules={[{ required: false, message: `请选择目录` }]}
			request={()=>folders}
		/>
	</FormWrap>),[folders])

  return <DialogForm
  	width={600}
		loading={loading}
		loadingText="保存中..."
		render={formElements} 
		initialValues={{parentId}}
		onFinish={onFinish} 
    title={title}
    
    ref={ref}
   >
		{children}
	</DialogForm>
};


Post.defaultProps = {
	onSuccess:function(){},
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