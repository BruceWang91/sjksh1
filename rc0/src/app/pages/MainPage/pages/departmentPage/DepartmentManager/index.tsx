





import React , {useCallback,useEffect,useState} from 'react';
import type { ColumnsType } from 'antd/es/table';
import type { TableRowSelection } from 'antd/es/table/interface';
import {Page} from 'app/components/Page';
import { Table , Space ,Modal,Button,message} from 'antd';
import Icon, { ImportOutlined,PlusOutlined ,ApartmentOutlined ,UserSwitchOutlined } from '@ant-design/icons';
import {RowDateTime} from 'app/components/RowDateTime';


import { useDispatch, useSelector } from 'react-redux';
import { useDepartmentSlice } from '../slice';
import { selectOrgId } from '../../../slice/selectors';
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

import {Post} from './Post';
import {DialogConfirm} from 'app/components/DialogConfirm';





/*const rowSelection: TableRowSelection<DataType> = {
  onChange: (selectedRowKeys, selectedRows) => {
    console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows);
  },
  onSelect: (record, selected, selectedRows) => {
    console.log(record, selected, selectedRows);
  },
  onSelectAll: (selected, selectedRows, changeRows) => {
    console.log(selected, selectedRows, changeRows);
  },
};
*/

export const DepartmentManager: React.FC = () => {
 	useDepartmentSlice();
  //const [checkStrictly, setCheckStrictly] = useState(false);
  const dispatch = useDispatch();
  const orgId = useSelector(selectOrgId);
  const list = useSelector(selectDepartments);
  const loading = useSelector(selectDepartmentListLoading);

  const fetch = useCallback(()=>{
  	dispatch(getDepartments({orgId,parentId:0}));
  },[orgId,dispatch])
  
  useEffect(() => {
  	fetch()
  }, []);


  const delRecord = (id) => {
  	const hide = message.loading('正在删除')
  	dispatch(
     	deleteDepartment({
        id,
        resolve: () => {
        	fetch();
        	hide();
          message.success('删除成功');
        },
      }),
    );
  }



const columns: ColumnsType<DepartmentViewModel> = [
	{
		title:'组织',
		dataIndex:'deptName',
		width:300,

	},

	{
		title:'显示顺序',
		dataIndex:'orderNum',
		ellipsis: true,
	},
	{
		title:'创建时间',
		dataIndex:'createTime',
		ellipsis: true,
		render: (_, record) => <RowDateTime date={_} /> ,
	},
	{
		title:'修改时间',
		dataIndex:'updateTime',
		ellipsis: true,
		render: (_, record) => <RowDateTime date={_} /> ,
	},

	{
		title:'',
		dataIndex:'action',
		render: (_, record) => {
     return (<Space size="middle">
       	<Post title={`修改<${record.deptName}>`} data={record} onSuccess={fetch}><a>编辑</a></Post>
       	<Post key="add" title="创建企业/分组/部门" data={{parentId:record.deptId}} onSuccess={fetch}><a>创建企业/分组/部门</a></Post>
       	<DialogConfirm key="delete" onOk={event=>delRecord(record.deptId)} ><a>删除</a></DialogConfirm>
      </Space>
    )},
		width:250
	}
];
 

  return <Page
				queries={{}}
				loading={loading}
				query={null}
				toolbar={{
					onRefresh:fetch,
					title:'组织管理',
					buttons:[<Post key="add"  data={{parentId:0}} title="创建企业/分组" onSuccess={fetch}><Button key="add" size="small" type="primary" icon={<PlusOutlined />}>创建企业/分组</Button></Post>]
				}}
				pagination={null}
			>
      <Table
      	size="small"
      	rowKey={record=>record.deptId}
        columns={columns}
        //icon={(_)=>_.isFolder ?  <ApartmentOutlined /> : <UserSwitchOutlined />}
        //rowSelection={{ ...rowSelection, checkStrictly }}
        dataSource={list}
        pagination={false}
        
      />
    </Page>
};