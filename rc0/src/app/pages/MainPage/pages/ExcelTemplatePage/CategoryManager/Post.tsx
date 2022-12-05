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
  ProFormTreeSelect
} from '@ant-design/pro-components';

import {message} from 'antd';

import { useDispatch, useSelector } from 'react-redux';
import { useCategorySlice } from './slice';
import { selectOrgId } from '../../../slice/selectors';
import { CommonFormTypes, TIME_FORMATTER } from 'globalConstants';

import {
  selectDeleteCategoriesLoading,
  selectSaveCategoryLoading,
  selectCategoryListLoading,
  selectCategories,
} from './slice/selectors';
import {
  addCategory,
  deleteCategory,
  editCategory,
  getCategories,
} from './slice/thunks';
import {
  Category,
  CategoryViewModel,
} from './slice/types';


const renderForm = ( {isRoot,categories} ) => () => {
	const nameLabel = isRoot ? "企业名称" : "部门名称";


	return <FormWrap>
		
		<ProForm.Group>

			<ProFormText 
				width="sm"
				name="name" 
				fieldProps={{size:'small'}}
				label={ '分类名称' }
				placeholder={'分类名称'}
				rules={[{ required: true, message: `请输入分类名称` }]}
			/>
			<ProFormDigit
				width="sm"
				name="orderNum" 
				fieldProps={{size:'small'}}
				label={ "显示顺序"}
				placeholder="显示顺序"
				rules={[{ required: true, message: `请输入显示顺序` }]}
			/>

			{!isRoot && <ProFormTreeSelect
				name="parentId" 
				fieldProps={{size:'small',style:{minWidth:200}}}
				label={  "上级" }
				placeholder={"请选择上级分类"}
				rules={[{ required: true, message: `请选择${"上级"}分类` }]}
				request={()=>categories}
			/>}
    </ProForm.Group>
   	
    <Descp>
    	<h5>注意事项</h5>
    	<p>1、显示顺序按升序排序；</p>
			
    </Descp>
	</FormWrap>
}

interface PostProps{
	data:Category;
	children:React.ReactNode;
	title?:string;
	onSuccess?:()=>void;
}

export const Post: React.FC = ({ children,title, data , onSuccess }) => {

	useCategorySlice();
	const isRoot = !!!data?.parentId
	const dispatch = useDispatch();
	const orgId = useSelector(selectOrgId);
 	const ref = useRef();
 	const loading = useSelector(selectSaveCategoryLoading);


 	const categories = useSelector(selectCategories);


 	const onFinish = ( values ) => {
 		if (!data?.id) {
      dispatch(
        addCategory({
          category: {...data!, ...values },
          resolve: () => {
          	onSuccess()
          	ref.current.hide()
            message.success('保存成功');
          },
        }),
      );
    } else {
      dispatch(
       	editCategory({
          category: { ...data!, ...values },
          resolve: () => {
          	onSuccess()
          	ref.current.hide()
            message.success('保存成功');
          },
        }),
      );
    }
 	};

 	const onOpen = useCallback(()=>{

 		dispatch(getCategories({parentId:0,orgId}))
 	},[dispatch,orgId])
  return <DialogForm
  	
		loading={loading}
		loadingText="保存中..."
		render={renderForm({isRoot,categories})} 
		initialValues={ data }
		onFinish={onFinish} 
    title={title}
    onOpen={onOpen}
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