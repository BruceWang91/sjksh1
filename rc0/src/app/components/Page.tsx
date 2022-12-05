import React , {useEffect,useState,useRef} from 'react';
import { LoadingOutlined } from '@ant-design/icons';

import { Pagination ,Spin , Card } from 'antd';
import { Query } from './Query';
import { Toolbar } from './Toolbar';
import isPlainObject from 'lodash/isPlainObject';
import styled from 'styled-components/macro';
import {
  FONT_SIZE_HEADING,
  FONT_SIZE_ICON_MD,
  SPACE_SM,
  SPACE_LG,
  BORDER_RADIUS ,
  SPACE_TIMES
} from 'styles/StyleConstants';


const antIcon = <LoadingOutlined style={{ fontSize: 24 }} spin />;


const queryDefaultProps = {
	fields:[],
	labelWidth:90
};
const breadcrumbDefaultProps = {
	paths:[],
	last:null
};
const paginationDefaultProps = {
	size:'small',
	total:1,
	current:1,
	pageSize:20,
};
const toolbarDefaultProps = {
	onRefresh:function onRefresh(){},
	buttons:[]
};

export const Page = ({
	children,
	onQueriesChange,
	queries,
	upperRouterQueryNames,
	loading,
	toolbar,
	pagination,
	query,
	viewPortHeight,
	fixedHeight,
	...rest}) => {



	const [viewHeight,setViewHeight] = useState(100);

	const onResize = (queryHeight) => {
		let footerHeight = 0,toolbarHeight = 0;
		
		if(pagination){
			footerHeight = 57;
		}
		if(toolbar){
			toolbarHeight = 50;
		}
		setViewHeight((viewPortHeight === 'auto' ? document.body.clientHeight : viewPortHeight)  - queryHeight - footerHeight - toolbarHeight - 80)
	}

	const onPaginationChange = (pageNum,pageSize) => {
		const qs = {...queries,pageNum,pageSize}
		onQueriesChange(qs);
  };


  const onSearch = ( values ) => {
		const qs = {...queries,...values}
		onQueriesChange(qs);
  };

  let paginationProps = null;

  if(pagination){
  	paginationProps = {
	  	current:queries.pageNum || pagination?.current,
	  	total: pagination?.total || 100,
	  	onChange: onPaginationChange ,
	    pageSize: queries.pageSize || pagination?.pageSize || 20  ,
	    size: pagination?.size || 'small'
	  };
  }

  useEffect(()=>{
  	if(!query){
  		onResize(0)
  	}
  },[])


  const child = React.Children.only(children);
  
  const childProps = {...child.props};

  if(isPlainObject(childProps.scroll)){
  	childProps.scroll = {...childProps.scroll,y:fixedHeight||viewHeight}
  }


  const newChild = React.cloneElement(child, childProps);

	return <Wrap>
		<Spin spinning={loading} indicator={antIcon}>
			<div className="container-wrap">
				{query && <Query  {...queryDefaultProps} {...query} onSearch={onSearch} onResize={onResize} size="small" />}
				
		    <Card>
		    {toolbar && <Toolbar queryNames={upperRouterQueryNames} {...toolbarDefaultProps} {...toolbar} >{toolbar.buttons}</Toolbar>}
		    {newChild}
			  {
			  	paginationProps && (<PaginationWrap>
				  	<Pagination {...paginationProps} />
				  </PaginationWrap>)
				}
				</Card>
			</div>
	  </Spin>
	</Wrap>
}


Page.defaultProps = {
	queries:{},
	upperRouterQueryNames:[],
	onQueriesChange:function onQueriesChange(){},
	toolbar:toolbarDefaultProps,
	query:queryDefaultProps,
	pagination:paginationDefaultProps,
	viewPortHeight:'auto'
}



const Wrap = styled.div`
  flex: 1;
  width:100%;
  padding: 0 ${SPACE_TIMES(2)} 0 ${SPACE_TIMES(1)};
  .ant-card {
    background-color: ${p => p.theme.componentBackground};
    border-radius: ${BORDER_RADIUS};
    box-shadow: ${p => p.theme.shadow1};

    .ant-card-body {
      padding: ${SPACE_TIMES(3)};
    }
  }
`;

const PaginationWrap=styled.div`
  text-align: right;
	padding:${SPACE_TIMES(3)} 0 0  0;
	border-top: 1px solid ${p => p.theme.borderColorBase};
`;

