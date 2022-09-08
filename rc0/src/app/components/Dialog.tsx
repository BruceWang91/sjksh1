import React from 'react';
import { LoadingOutlined } from '@ant-design/icons';
import { Spin , Modal } from 'antd';

const antIcon = <LoadingOutlined style={{ fontSize: 24 }} spin />;

interface IDialogProps {

  render : ()=> any | React.ReactNode ;
	onOk: ()=> void ;
	onCancel:  ()=> void ;
	onShow : ()=> void ;
	loading:  boolean ;
	loadingText: string;
}

/**
 * @Author   Zuojin(463609@qq.com)
 * @DateTime 2021-11-24
 * @param    {[type]}              options.children
 * @param    {...[type]}           options.rest
 * @return   {[type]}
 */
export class Dialog extends React.Component<IDialogProps,{}> {

	static defaultProps = {
		onShow:function(){},
		onCancel:function(){},
		onOk:function(){},
		render:function(){},
		loadingText:'Loading...',
		loading:false
	}; 
	state = {
		visible: false ,
	}
  constructor( props:IDialogProps) {
    super(props);
    this.onOk = this.onOk.bind(this);
    this.onCancel = this.onCancel.bind(this);

  }
  onOk(){
  	if(!this.props.loading){
  		this.show();
  		this.props.onOk()
  	}
  }
  onCancel(){

  	if(!this.props.loading){
  		this.hide();
  		this.props.onCancel()
  	}
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
			...rest
    } = this.props;

    const {visible} = this.state;
    const child = React.Children.only(children);
    
    const newChild = React.cloneElement(child, { ...child.props,
			onClick: event => {
				
				this.props.onShow();
				this.show()
			}
		});

    return <React.Fragment>
			<Modal
				{...rest}
				size="small"
				disabled={loading}
	      visible={visible}
	      onCancel={ this.onCancel }
	      onOk={ this.onOk }
	    >
	    	<Spin spinning={loading} tip={loadingText} indicator={antIcon} >
	    		{typeof render === 'function' ? render({visible}) : render }
	    	</Spin>
	    	
	    </Modal>
			{newChild}
		</React.Fragment>
  }
}

