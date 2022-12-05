import React from 'react';
import { Modal } from 'antd';


interface IDialogProps {
	content?: string;
	title?: string;
	onOk:()=>void;
	onCancel:()=>void;
}

const size = { size: 'small'}
/**
 * @Author   Zuojin(463609@qq.com)
 * @DateTime 2021-11-24
 * @param    {[type]}              options.children
 * @param    {...[type]}           options.rest
 * @return   {[type]}
 */
export class DialogConfirm extends React.Component<IDialogProps,{}> {

	static defaultProps = {
		title:"确认要删除?",
		content:"删除后无法恢复，您确定要删除？",
		onOk:function(){},
		onCancel:function(){}
	}; 
	state = {}
  constructor( props:IDialogProps) {
    super(props);
    this.onOk = this.onOk.bind(this)
  }
  componentWillUnmount(){
  	if(this.dialog){
  		this.dialog.destroy()
  	}
  }
  onOk(){

  	this.props.onOk && this.props.onOk();

  }
  render() {
    const {
      children,
      title,
      content,
			onOk,
			onCancel,
			...rest
    } = this.props;

    const child = React.Children.only(children);


    const newChild = React.cloneElement(child, { ...child.props,
			onClick: event => {

				if(!this.dialog){
					this.dialog = Modal.confirm({
						title,
						content,
						okButtonProps:size,
						cancelButtonProps:size,
						onOk:this.onOk,
						onCancel
					})
				}else{
					this.dialog.update({
						title,
						content,
						onOk:this.onOk,
						okButtonProps:size,
						cancelButtonProps:size,
						onCancel,
						visible:true
					})
				}
				
			}
		});

    return <React.Fragment>
			{newChild}
		</React.Fragment>
  }
}

