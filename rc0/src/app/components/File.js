import React  from "react";
import {Input,Button} from 'antd';
import {
UploadOutlined
} from '@ant-design/icons';
interface FileProps{
	value:any;
	defaultValue:any;
	onChange:()=>void;
}

export class File extends React.Component<FileProps> {
	state = {
		file: this.props.value || this.props.defaultValue
	}
	static getDerivedStateFromProps(nextProps, prevState) {
    const { value } = nextProps;
    if (value !== prevState.value) {
      return {
        value,
      }
    }
    return null;
	}
  constructor(props) {
    super(props)
    this.inputRef = React.createRef();
    this.onClick = this.onClick.bind(this)
  }
  onClick(){
  	this.inputRef.current.click();
  }
  render() {
    const {
      onChange,
      ...rest
    } = this.props
    return (
      <React.Fragment>
        <Button icon={<UploadOutlined />} size="small" onClick={this.onClick}>{this.state.file ? '重新选择' : '选择文件'}</Button> <span>{this.state?.file?.name}</span>
        <input   ref={this.inputRef} type="file" onChange={event=>{
        	if(event.target.files[0]){
        		this.setState({file:event.target.files[0]});
        		onChange(event.target.files[0])
        	}
        	event.target.value = ''
        }} style={{display:'none'}} />
      </React.Fragment>
    )
  }
}

File.defaultProps = {
	value:null,
	defaultValue:null,
	onChange: function onChange(){}
}