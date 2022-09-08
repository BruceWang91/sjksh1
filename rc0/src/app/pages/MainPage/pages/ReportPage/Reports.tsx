import React , {useCallback,useMemo ,useState ,useEffect} from 'react';



import {Page} from 'app/components/Page';
import {getInitialValues} from 'app/components/Query';


import { Table , Space ,Modal,Button,message} from 'antd';
import Icon, { ImportOutlined } from '@ant-design/icons';
import {RowDateTime} from 'app/components/RowDateTime';

import { useHistory } from 'react-router';

import { useDispatch, useSelector } from 'react-redux';
import { useReportSlice } from './slice';
import { selectOrgId } from '../../slice/selectors';
import {
  selectReportDataLoading,
  selectReportListLoading,
  selectReportData,
  selectReports,
  selectTotalRecords,
} from './slice/selectors';
import {
  getReportData,
  getReports,
  initReport
} from './slice/thunks';




import {
  getDepartments,
} from '../departmentPage/slice/thunks';



const dataKey:string = 'id';


	
export const Reports = ({type}) => {

	useReportSlice();

	const history = useHistory();
	const dispatch = useDispatch();

	const fields = useMemo(()=>[{
		alias:'报表名称',
		name:'name',
		componentType:'Text',
		initialValue:null,
	}],
	[dispatch])

	const [queries,setQueries] = useState({...getInitialValues(fields),type:type,pageSize:20,pageNum:1,template:0})
	
	
  const orgId = useSelector(selectOrgId);
  const list = useSelector(selectReports);
  const totalRecords = useSelector(selectTotalRecords);

  const loading = useSelector(selectReportListLoading);
  
  const fetch = useCallback((qs)=>{

  	dispatch(getReports({...queries,...(qs||{})}));
  },[orgId,dispatch,queries])
  
  useEffect(() => {
  	fetch({type})
  }, [type]);
	

	const columns = [
		
		{
			title:'报表名称',
			dataIndex:'name',
			ellipsis: true,
			width:'40%',
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
	     		<a onClick={event=>history.push(`/organizations/${orgId}/dataReport/editor?id=${record.id}&module=view&name=${record.name}`)}>查看</a>
	       	<a onClick={event=>history.push(`/organizations/${orgId}/dataReport/editor?id=${record.id}&module=index&name=${record.name}`)}>编辑</a>
      	</Space>
	     	
	    )},
			width:150
		}
	];

	
	const onCreateNew = ( ) => {
		dispatch(initReport({
			report:{},
			resolve:(result)=>{
				history.push(`/organizations/${orgId}/dataReport/editor?id=${result.id}&module=index&name=新建报表`)
			}
		}));
  };
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
			buttons:[<Button onClick={onCreateNew} type="primary" size="small">新建报表</Button>]
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





