import React , {useCallback,useMemo ,useState ,useEffect} from 'react';
import {Post} from './Post';
import {UpdateInfo} from './UpdateInfo';
import {ImportData} from './ImportData';
import {Browser} from '../Browser';
import {DialogConfirm} from 'app/components/DialogConfirm';

import {Page} from 'app/components/Page';
import {getInitialValues} from 'app/components/Query';


import { Table , Space ,Modal,Button,message} from 'antd';
import Icon, { ImportOutlined } from '@ant-design/icons';
import {RowDateTime} from 'app/components/RowDateTime';


import { useDispatch, useSelector } from 'react-redux';
import { useFilemainSlice } from './slice';
import { selectOrgId } from '../../../slice/selectors';
import {
  selectDeleteFilemainsLoading,
  selectSaveFilemainLoading,
  selectFilemainListLoading,
  selectFilemains,
  selectTotalRecords,
} from './slice/selectors';
import {
  addFilemain,
  deleteFilemain,
  editFilemain,
  getFilemains,
  getFilemain,
} from './slice/thunks';
import {
  Filemain,
  FilemainViewModel,
} from './slice/types';
import {serverDataconvertToExcel ,exportXLSX} from '../utils';



import {
  getCategories,
} from '../CategoryManager/slice/thunks';


import {
  getDepartments,
} from '../../departmentPage/slice/thunks';



const dataKey:string = 'fileId';


	
export const ExcelManager = ({}) => {

	useFilemainSlice();

	const dispatch = useDispatch();

	const fields = useMemo(()=>[{
		alias:'名称',
		name:'fileName',
		componentType:'Text',
		initialValue:null,
	},{
		alias:'分类',
		name:'classId',
		componentType:'TreeSelect',
		initialValue:null,
		placeholder:"请选择分类",			
		request:( {} )=>{
			return new Promise((resolve,reject)=>{
				dispatch(
					getCategories({orgId,parentId:0,resolve:data=>{
						resolve(data)
					}})
				)
			})
		}
	},{
		alias:'组织',
		name:'deptId',
		componentType:'TreeSelect',
		initialValue:null,
		placeholder:"请选择组织",			
		request:( {} )=>{
			return new Promise((resolve,reject)=>{
				dispatch(
					getDepartments({orgId,parentId:0,resolve:data=>{
						resolve(data)
					}})
				)
			})
		}
	}],
	[dispatch])

	const [queries,setQueries] = useState({...getInitialValues(fields),pageSize:20,pageNum:1})
	
	
  const orgId = useSelector(selectOrgId);
  const list = useSelector(selectFilemains);
  const totalRecords = useSelector(selectTotalRecords);

  const loading = useSelector(selectFilemainListLoading);
  
  const fetch = useCallback((qs)=>{

  	dispatch(getFilemains({...queries,...(qs||{})}));
  },[orgId,dispatch,queries])
  
  useEffect(() => {
  	fetch()
  }, []);
	

	 const delRecords = ( ids ) => {
  	const hide = message.loading('正在删除',0)
  	dispatch(
     	deleteFilemain({
        ids,
        resolve: () => {
        	fetch();
        	hide();
          message.success('删除成功');
        },
      }),
    );
  }

  const exportByRecord = ( record ) => {
  	const hide = message.loading('导出表样中...',0)
  	dispatch(
     	getFilemain({
     		fileId:record.fileId,
        resolve: (data) => {
        	hide()
	    		exportXLSX(serverDataconvertToExcel(data.sheets),record.fileName+'.xlsx');
        },
      }),
    );
  }


	const columns = [
		{
			title:'编号',
			dataIndex:'fileId',
			width:60
		},
		{
			title:'文件名称',
			dataIndex:'fileName',
			ellipsis: true,
			width:'40%',
		},
		{
			title:'显示顺序',
			dataIndex:'orderNum',
			ellipsis: true,
		},
		/*{
			title:'文件状态',
			dataIndex:'status',
			ellipsis: true,
		},*/
		{
			title:'备注',
			dataIndex:'remark',
			ellipsis: true,
		},
		
		
		{
			title:'',
			dataIndex:'action',
			render: (_, record) => {
	     return (
	     	<div>
	     		<div>
	     			
	     			<Space size="middle">
			       	<Browser title={`预览表样<${record.fileName}>`} fileId={record.fileId} ><a>预览表样</a></Browser>
			       	<a onClick={event=>exportByRecord(record)}>导出表样</a>
			       	<UpdateInfo data={record} title={`编辑<${record.fileName}>`} onSuccess={()=>fetch()} ><a>编辑</a></UpdateInfo>
		      	</Space>
	     		</div>
	     		<div>
	     			
	     			<Space size="middle">
			       	<Post data={record} title={`更新表样<${record.fileName}>`} onSuccess={event=>fetch()} ><a>更新表样</a></Post>
			       	
			       	<ImportData title={`导入数据<${record.fileName}>`} data={record}><a>导入数据</a></ImportData>
			       	<DialogConfirm onOk={event=>delRecords([record.fileId])}  content={`是否确认删除文件管理编号为"${record.fileId}"的表样文件？`} ><a>删除</a></DialogConfirm>
		      	</Space>
	     		</div>
	     	</div>
	     	
	    )},
			width:250
		}
	];


  const onQueriesChange = ( qs ) => {
		setQueries(qs);
		fetch(qs)
  };

	return <Page
		queries={queries}
		loading={loading}
		onQueriesChange={onQueriesChange}
		query={{
			fields:fields
		}}
		
		toolbar={{
			onRefresh:()=>fetch(),
			buttons:[<Post  key="upload" title="上传表样" onSuccess={event=>fetch()}><Button type="primary" size="small">上传表样</Button></Post>]
		}}
		pagination={{
			total:totalRecords,
		}}
	>
		
		<Table
			size="small"
	    columns={columns}
	    dataSource={list}
	    rowKey={record => record[dataKey] }
	    pagination={false}
	    scroll={{
	      y: 100,
	    }}
	  />

	</Page>
}





