import { Split } from 'app/components';
import React, { useCallback, useState } from 'react';
import styled from 'styled-components/macro';
import { Main } from './Main';
import { Sider } from '../../../Sider';

import {Layout,Resizer} from 'react-zj';
import { selectOrgId } from '../../../slice/selectors';
import { useDispatch, useSelector } from 'react-redux';
import {useHistory} from 'react-router';
import {
  FileExcelOutlined,
} from '@ant-design/icons';
import { ResourceTypes ,PermissionLevels} from '../../PermissionPage/constants';
export function ExcelManager() {
	const orgId = useSelector(selectOrgId);
	const history = useHistory();
	const onSelect = useCallback((node)=>{
		//console.log(node)
		history.push(
      `/organizations/${orgId}/exceltemplates?id=${node.value}`,
    );
	},[history,orgId])
  return (
  	<Resizer>{ props =>(<Layout {...props} type={Layout.HORIZONTAL}  >
		<Layout.Item size={293}>
    	{ ()=><Sider title="数据导入" dataSourceType={ResourceTypes.ExcelTemplate} level={PermissionLevels.Create} icon={<FileExcelOutlined />} onSelect={onSelect} />}
    </Layout.Item>
    <Layout.Item>
    	{ ()=><Main  />}
    </Layout.Item>
  </Layout>)}</Resizer>
  );
}
