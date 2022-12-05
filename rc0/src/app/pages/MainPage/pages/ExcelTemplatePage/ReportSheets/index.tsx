import { Split } from 'app/components';
import React, { useCallback, useState } from 'react';
import styled from 'styled-components/macro';
import { Main } from './Main';
import { Sider } from '../../../Sider';

import {Layout,Resizer} from 'react-zj';
import { selectOrgId } from '../../../slice/selectors';
import { useDispatch, useSelector } from 'react-redux';
import {useHistory} from 'react-router';
	
export function ReportSheets() {
	const orgId = useSelector(selectOrgId);
	const history = useHistory();
	const onSelect = useCallback((node)=>{
		//console.log(node)
		history.push(
      `/organizations/${orgId}/excelviews?id=${node.value}`,
    );
	},[history,orgId])
  return (
  	<Resizer>{ props =>(<Layout {...props} type={Layout.HORIZONTAL}  >
		<Layout.Item size={275}>
    	{ ()=><Sider  title="数据预览" onSelect={onSelect} />}
    </Layout.Item>
    <Layout.Item>
    	{ ()=><Main  />}
    </Layout.Item>
  </Layout>)}</Resizer>
  );
}
