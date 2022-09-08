import React , {useMemo,useEffect,useState,useRef} from 'react';
import * as RestComponents from '@ant-design/pro-components';
import { LoadingOutlined } from '@ant-design/icons';
import cloneDeep from 'lodash/cloneDeep';
import debounce  from 'lodash/debounce';
import { Spin,Card } from 'antd';

import styled from 'styled-components/macro';
import {
  FONT_SIZE_HEADING,
  FONT_SIZE_ICON_MD,
  SPACE_SM,
  SPACE_LG,
  BORDER_RADIUS ,
  SPACE_TIMES
} from 'styles/StyleConstants';

export const getInitialValues = ( fields , initialValueFromSearchNames = [] ) => {
	const ret = {}
	fields.forEach(field=>{
		ret[field.name] = field.initialValue
		if(initialValueFromSearchNames.indexOf(field.name) > -1){
			ret[field.name] =  field.initialValue
		}
	})
	return ret
}


const antIcon = <LoadingOutlined style={{ fontSize: 24 }} spin />;


const transData = ( array ) => {
	if(!Array.isArray(array)){
		return []
	}
	return (array||[]).map(d=>({...d,title:d.displayValue,label:d.displayValue,value:d.hideValue,key:d.hideValue,isLeaf:d.leaf === 0}))
}

const getNode = ( array , key ) => {
	let ret = null;
	if(ret = array.find(item=>item.key === key)){
		return ret;
	}
	for(let i=0,len = array.length; i<len;i++){
		if(Array.isArray(array[i].children)){
			if(ret = getNode(array[i].children,key)){
				return ret
			}
		}
	}
	return ret;
}


export const Query = ({
	onCollapse,
	onSearch,
	user,
	fields,
	ref,
	onResize,
	...rest}) => {


	const querybarRef = useRef(ref);
	const [collapsed,setCollapsed] = useState(false);
	const [components,setComponents] = useState([]);
	const [loading,setLoading] = useState(true);

	const initialValues = useMemo(()=>getInitialValues(fields),[fields]);

	useEffect(()=>{
		querybarRef.current && onResize(querybarRef.current.offsetHeight + 12)
	},[collapsed,components])

	const onQueryFilterCollapse = ( collapsed ) => {
		setCollapsed(collapsed)
	}


	useEffect(()=>{

		const components = cloneDeep(fields.filter(field=> field.visible !== false ));
		const componentsMap = {};

		

		const upmaps = (_components) => {
			_components.forEach(component=>{
				componentsMap[component.name] = component
			})
		}


		upmaps(components)

		const load = async () => {

			//const total = components.filter(c=> !c.requestBy && typeof c.request === 'function' ).length;
			setLoading(true)
			for(let i = 0; i < components.length; i ++){

				const component = components[i];
				const {componentType,name,request} = component;

				if(componentType === 'TreeSelect' && typeof request === 'function'){

					if(!component.requestBy){
						
						component.options = transData(await request({user,value:null}));
					}
					
					component._$expandLoader = (node) => {
							
						return new Promise(( resolve , reject)=>{
							const childLoad = async () => {
								if(node?.children?.length > 0 ){
									resolve(node)
									return;
								}
								const childOptions = transData(await request({user,value:node.key}));
								const child = getNode(component.options,node.key);
								child.children = childOptions;
								const newComponents = cloneDeep(components);
								upmaps(newComponents)
								setComponents(newComponents)
								resolve(child)
							}
							childLoad();
						})
					}


				}else{

					if(!component.requestBy && typeof request === 'function'){
						
						component.options = transData(await request({user,value:null}));
					}
				}

				if(component.requestBy && componentsMap[component.requestBy] && typeof request === 'function'){

					const targetComponent = componentsMap[component.requestBy];

					if(!Array.isArray(targetComponent._$linkages)){
						targetComponent._$linkages = [];
					}

					targetComponent._$linkages = [...targetComponent._$linkages.filter( c => c.name !== component.name),component];
					
					if(!targetComponent._$onChange){
						targetComponent._$onChange = ( value ) => {

							const linkLoad = async ()=> {
								for(let i=0; i<targetComponent._$linkages.length; i++){
									const component = targetComponent._$linkages[i];
									component.options = transData(await component.request({user,value}));;
								}
								const newComponents = cloneDeep(components);
								upmaps(newComponents)
								setComponents(newComponents)
							}
							linkLoad();
						}
					}
				}
			}
			
			const newComponents = cloneDeep(components);
			upmaps(newComponents)
			setComponents(newComponents)

			setLoading(false)
		}

		load();
	},[fields])


	useEffect(()=>{

  	//节流
  	const resize = debounce(()=>{
  		querybarRef.current && onResize(querybarRef.current.offsetHeight + 12)
  	},300)
  	
  	window.addEventListener('resize',resize)

  	return function() {
  		window.removeEventListener('resize',resize)
  	}

  },[querybarRef])





	return <Wrap  ref={querybarRef}>
	<Card>
	<Spin spinning={loading} indicator={antIcon}>
		<RestComponents.QueryFilter {...rest} defaultCollapsed={false} onFinish={onSearch} initialValues={initialValues} collapsed={collapsed}	defaultCollapsed onCollapse={onQueryFilterCollapse} split >
			{
				components.map( component=>{

					const fieldProps = {...(component.props || {}),size:'small'};

					if(typeof component._$expandLoader === 'function'){
						fieldProps.loadData = component._$expandLoader
					}
					if(component.options){
						fieldProps.options = component.options
					}
					if(typeof component._$onChange === 'function'){
						fieldProps.onChange = component._$onChange
					}
					const ComponentTag = RestComponents[`ProForm${component.componentType}`];
					return <ComponentTag
						key={component.name}
		      	name={component.name}
		      	label={component.alias}
		      	fieldProps={fieldProps}
		      	placeholder={component.placeholder}

		      />
				})
			}
    </RestComponents.QueryFilter>

   </Spin>
   </Card>
  </Wrap>
}

Query.defaultProps = {
	onSearch: function  onSearch(){},
	onResize: function onResize(){},
	fields:[]
}

const Wrap = styled.div`
  
  margin-bottom:${SPACE_TIMES(1)};
  padding:0;
  .ant-card {
    background-color: ${p => p.theme.componentBackground};
    border-radius: ${BORDER_RADIUS};
    box-shadow: ${p => p.theme.shadow1};

    .ant-card-body {
      padding: ${SPACE_TIMES(4)} ${SPACE_TIMES(3)} 0 ${SPACE_TIMES(3)} !important;
    }
  }

  .ant-form-item{
  	margin-bottom:${SPACE_TIMES(4)}
  }
  .ant-divider-horizontal{
  	margin-top:-${SPACE_TIMES(1)} !important;
    margin-bottom:${SPACE_TIMES(2)} !important;
  }
`;