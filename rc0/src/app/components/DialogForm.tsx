import React from 'react';
import {ModalForm} from '@ant-design/pro-components';
import { LoadingOutlined } from '@ant-design/icons';
import { Spin ,Tooltip} from 'antd';

const antIcon = <LoadingOutlined style={{ fontSize: 24 }} spin />;

interface DialogFormProps {
	render:()=>any;
	onOpen : ()=> void ;
	loading:boolean;
	loadingText:string;
	tooltip:string;
}
/**
 * @Author   Zuojin(463609@qq.com)
 * @DateTime 2021-11-24
 * @param    {[type]}              options.children
 * @param    {...[type]}           options.rest
 * @return   {[type]}
 */
export class DialogForm extends React.Component <DialogFormProps,{}> {
	static defaultProps = {
		render : function(){},
		onOpen:function(){},
		loading: false,
		loadingText: 'Loading...',
		tooltip:'',
		destroyOnClose:true
	}
	state = {
		visible: false ,
	}
  constructor(props:DialogFormProps) {
    super(props);
    this.onVisibleChange = this.onVisibleChange.bind(this);
    this.formRef = React.createRef();
  }
  onVisibleChange( bool ){

  	this.setState({
  		visible: this.props.loading || bool
  	})
  }
  show(){
  	this.setState({visible:true})
  }
  hide(){
  	this.setState({visible:false})
  }
  render() {
    const {
      children,
			render,
			loading,
			loadingText,
			tooltip,
			destroyOnClose,
			...rest
    } = this.props;

    let trigger = null;

    const {visible} = this.state;


    if(children){
    	const child = React.Children.only(children);
    
	    const newChild = React.cloneElement(child, { ...child.props,
				onClick: event => {
					if(event.domEvent){
						event.domEvent.preventDefault()
					}else{
						event?.preventDefault();
					}
					
					this.props.onOpen();
					this.show()
				}
			});
			
			trigger = tooltip ? <Tooltip title={tooltip} placement="bottom">{newChild}</Tooltip> : newChild;
    }
    
    return <React.Fragment>
			<ModalForm
				{...rest}
				modalProps={{destroyOnClose,...rest,maskClosable:false}}
				size="small"
				formRef={this.formRef}
				disabled={loading}
	      visible={visible}
	      onVisibleChange={ this.onVisibleChange }
	    >
	    	<Spin spinning={loading} tip={loadingText} indicator={antIcon} >
	    		{typeof render === 'function' ? render({visible}) : render }
	    	</Spin>
	    </ModalForm>
			{trigger}
		</React.Fragment>
  }
}

