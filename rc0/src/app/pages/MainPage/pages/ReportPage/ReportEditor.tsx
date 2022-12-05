import React,{useRef,useState } from 'react';
import { Toolbar } from 'app/components/Toolbar';
import styled from 'styled-components/macro';
import { Button,Spin} from 'antd';
import { LoadingOutlined } from '@ant-design/icons';

import {
  FONT_SIZE_HEADING,
  FONT_SIZE_ICON_MD,
  SPACE_SM,
  SPACE_LG,
  BORDER_RADIUS ,
  SPACE_TIMES
} from 'styles/StyleConstants';


import { useHistory } from 'react-router';


const antIcon = <LoadingOutlined style={{ fontSize: 50 }} spin />;
export const ReportEditor = ({module,type,path,token,title,id,orgId}) => {
	const [loading,setLoading] = useState(true)
	const history = useHistory();
	const ref = useRef();
	const goback = () => {
		history.push(`/organizations/${orgId}/${path}`)
	}
	const onRefresh = () => {
		setLoading(true)
		ref.current?.contentWindow?.loaction?.reload()
	}
	return <Wrap>
		<Toolbar title={title} className="toolbar" onRefresh={onRefresh}>
			<Button key="goback" size="small" onClick={goback}>返回</Button>
		</Toolbar>
		<div className="iframe-container">
		<Spin spinning={loading} tip={``} indicator={antIcon} >
			<iframe ref={ref} onLoad={event=>setLoading(false)} src={`/jmreport/${module}/${id}?menuType=${type}&token=${token}`} frameBorder="0"  />
		</Spin>
		</div>
	</Wrap>
}


const Wrap = styled.div`
  
  position:fixed;
  top:0;
  width:100%;
  height:100%;
  background:${p => p.theme.componentBackground};
  z-index:10;

  .toolbar{
  	padding: 0 ${SPACE_TIMES(5)};
  	align-items:center;
  	height:${SPACE_TIMES(10)};
  }

  .iframe-container{
  	width:100%;
  	height: calc( 100% - ${SPACE_TIMES(10)} );
  	iframe{
  		width:100%;
  		height:100%
  	}
  }
  .ant-spin-container,
  .ant-spin-nested-loading{
  	height:100%
  }
`;
