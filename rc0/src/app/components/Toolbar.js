import React ,{useCallback,useMemo}  from 'react';
import Icon, { ReloadOutlined } from '@ant-design/icons';
import { Button} from 'antd';
import {
  FONT_SIZE_HEADING,
  FONT_SIZE_ICON_MD,
  SPACE_SM,
  SPACE_LG,
  BORDER_RADIUS ,
  SPACE_TIMES
} from 'styles/StyleConstants';
import styled from 'styled-components/macro';

export const Toolbar = ({children,queryNames,onRefresh,showRefresh,title,...rest}) => {


	return (<Wrap {...rest}>
		<div><b>{title }</b></div>
		<div>
			{children}
			{showRefresh && <Button onClick={onRefresh} size="small" icon={<ReloadOutlined />}>刷新</Button>}
		</div>
	</Wrap>)
}

Toolbar.defaultProps = {
	onRefresh: function(){},
	queryNames:[],
	title:null,
	showRefresh:true
}


const Wrap = styled.div`
  display: flex;
	justify-content: space-between;
	padding: 0 0 ${SPACE_TIMES(3)}  0;
	border-bottom: 1px solid ${p => p.theme.borderColorBase};
	> div > * {
		margin-left:10px;
	}
	> div > *:first-child {
		margin-left:0;
	}
`;