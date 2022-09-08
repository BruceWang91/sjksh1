import React , {useCallback,useMemo ,useState ,useEffect} from 'react';


import {View} from './View';

import {Page} from 'app/components/Page';
import {getInitialValues} from 'app/components/Query';


import { Table , Space ,Modal,Button,message} from 'antd';
import Icon, { ImportOutlined } from '@ant-design/icons';
import {RowDateTime} from 'app/components/RowDateTime';


import { useDispatch, useSelector } from 'react-redux';
import { useSheetSlice } from './slice';
import { selectOrgId } from '../../../slice/selectors';
import {
  selectSheetDataLoading,
  selectSheetListLoading,
  selectSheetData,
  selectSheets,
  selectTotalRecords,
} from './slice/selectors';
import {
  getSheetData,
  getSheets
} from './slice/thunks';

import {serverDataconvertToExcel ,exportXLSX} from '../utils';


import {
  getCategories,
} from '../CategoryManager/slice/thunks';


import {
  getDepartments,
} from '../../departmentPage/slice/thunks';



const dataKey:string = 'sheetId';


	
export const ReportSheets = ({}) => {

	useSheetSlice();

	const dispatch = useDispatch();

	const fields = useMemo(()=>[{
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
  const list = useSelector(selectSheets);
  const totalRecords = useSelector(selectTotalRecords);

  const loading = useSelector(selectSheetListLoading);
  
  const fetch = useCallback((qs)=>{

  	dispatch(getSheets({...queries,...(qs||{})}));
  },[orgId,dispatch,queries])
  
  useEffect(() => {
  	fetch()
  }, []);
	

  const exportByRecord = ( record ) => {
  	const hide = message.loading('导出表样中...',0)

  	dispatch(getSheetData({sheetId:record.sheetId,resolve: (result) => {
        

	    const {startRows,fieldList} = result.sheet ; 
		  const sheet = serverDataconvertToExcel([result.sheet])[0] ;
		  sheet.cells = [...sheet.cells,...listConvertToExcelData( result?.data?.[0],fieldList)]
  		exportXLSX([sheet],record.sheetName+'.xlsx')
  			hide()
    }}))
  	
  }

	const columns = [
		{
			title:'编号',
			dataIndex:'sheetId',
			width:60
		},
		{
			title:'工作簿名称',
			dataIndex:'sheetName',
			ellipsis: true,
			width:'40%',
		},
		{
			title:'表名',
			dataIndex:'entityName',
			ellipsis: true,
		},
		{
			title:'创建时间',
			dataIndex:'createTime',
			ellipsis: true,
			render: (_, record) => <RowDateTime date={_} /> ,
		},
		
		{
			title:'',
			dataIndex:'action',
			render: (_, record) => {
	     return (
	     	<Space size="middle">
	     		<View title={`表<${record.entityName}>`} sheetId={record.sheetId}><a>数据查询</a></View>
	       	<a onClick={event=>exportByRecord(record)}>导出数据</a>
      	</Space>
	     	
	    )},
			width:150
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





