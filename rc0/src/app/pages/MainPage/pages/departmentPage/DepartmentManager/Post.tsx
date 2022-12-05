import React, {
	useState,useCallback,useRef,useMemo
} from 'react';
import styled from 'styled-components/macro';
import {DialogForm} from 'app/components/DialogForm';
import {
  ProForm,
  ProFormSelect,
  ProFormText,
  ProFormDigit,
  ProFormTreeSelect,
  ProFormRadio
} from '@ant-design/pro-components';

import {message} from 'antd';

import { useDispatch, useSelector } from 'react-redux';
import { useDepartmentSlice } from '../slice';
import { selectOrgId } from '../../../slice/selectors';
import { CommonFormTypes, TIME_FORMATTER } from 'globalConstants';

import {
  selectDeleteDepartmentsLoading,
  selectSaveDepartmentLoading,
  selectDepartmentListLoading,
  selectDepartments,
} from '../slice/selectors';
import {
  addDepartment,
  deleteDepartment,
  editDepartment,
  getDepartments,
} from '../slice/thunks';
import {
  Department,
  DepartmentViewModel,
} from '../slice/types';


const renderForm = ( {isRoot,departments} ) => () => {
	const nameLabel = isRoot ? "企业名称" : "部门名称";

	const typeOptions = [{label:'分组',value:1}, {label:'企业',value:2},...(isRoot ? [] : [{label:'部门',value:3}])];
	return <FormWrap>
		
		<ProForm.Group>

			<ProFormText 
				width="sm"
				name="deptName" 
				fieldProps={{size:'small'}}
				label={ "组织" }
				placeholder={"组织"}
				rules={[{ required: true, message: `请输入组织` }]}
			/>
			<ProFormDigit
				width="sm"
				name="orderNum" 
				fieldProps={{size:'small',defaultValue:0}}
				label={ "显示顺序"}
				placeholder="显示顺序"
				rules={[{ required: true, message: `请输入显示顺序` }]}
			/>

			
    </ProForm.Group>
   	<ProForm.Group>
   		<ProFormRadio.Group
        label="类型"
        name="type"
        options={typeOptions}
        rules={[{ required: true, message: `请选择类型` }]}
      />

			{!isRoot && <ProFormTreeSelect
				name="parentId" 
				fieldProps={{size:'small',style:{minWidth:200}}}
				label={  "上级组织" }
				placeholder={""}
				rules={[{ required: true, message: `请选择${"上级组织"}` }]}
				request={()=>departments}
			/>}
    </ProForm.Group>
    <Descp>
    	<h5>注意事项</h5>
    	<p>1、显示顺序按升序排序；</p>
			
    </Descp>
	</FormWrap>
}

interface PostProps{
	data:Department;
	children:React.ReactNode;
	title?:string;
	onSuccess?:()=>void;
}

export const Post: React.FC = ({ children,title, data , onSuccess }) => {

	useDepartmentSlice();
	const isRoot = !!!data?.parentId
	const dispatch = useDispatch();
	const orgId = useSelector(selectOrgId);
 	const ref = useRef();
 	const loading = useSelector(selectSaveDepartmentLoading);


 	const departments = useSelector(selectDepartments);


 	const onFinish = ( values ) => {
 		if (!data?.deptId) {
      dispatch(
        addDepartment({
          department: {...data!, ...values },
          resolve: () => {
          	onSuccess()
          	ref.current.hide()
            message.success('保存成功');
          },
        }),
      );
    } else {
      dispatch(
       	editDepartment({
          department: { ...data!, ...values },
          resolve: () => {
          	onSuccess()
          	ref.current.hide()
            message.success('保存成功');
          },
        }),
      );
    }
 	};
 
  return <DialogForm
  	
		loading={loading}
		loadingText="保存中..."
		render={renderForm({isRoot,departments})} 
		initialValues={ data }
		onFinish={onFinish} 
    title={title}
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