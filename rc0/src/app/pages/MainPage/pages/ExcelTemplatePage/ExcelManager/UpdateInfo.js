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
  selectSaveFilemainLoading,
} from './slice/selectors';
import {
  editFilemain
} from './slice/thunks';
import {
  Filemain,
  FilemainViewModel,
} from './slice/types';


import {selectCategories} from '../CategoryManager/slice/selectors';


import { listToTree } from 'utils/utils';
import treeNodeIconFn from 'utils/treeNodeIconFn'
import {selectFolder} from 'app/pages/MainPage/Sider/slice/selectors';
import {ResourceTypes} from 'app/pages/MainPage/pages/PermissionPage/constants';

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

  const folderMap = useSelector(selectFolder);
 	const categories = useSelector(selectCategories);

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

	const formElements = useMemo(()=>(<>
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
  	<ProFormTextArea  
  		fieldProps={{size:"small"}} 
  		name="remark" 
  		label="备注 " 
  		placeholder="请输入备注" />
  </>),[folders,categories]);


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