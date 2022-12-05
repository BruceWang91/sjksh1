import React from 'react';
import moment from 'moment';
import { ClockCircleOutlined } from '@ant-design/icons';



export const RowDateTime = ({format, showIcon, date }) => {
	if(date){
		return <React.Fragment>{showIcon && <ClockCircleOutlined />} {moment(date).format(format)}</React.Fragment>
	}
	return '/'
}

RowDateTime.defaultProps = {
	format:'YYYY-MM-DD H:mm:ss',
	date: new Date,
	showIcon:true,
}