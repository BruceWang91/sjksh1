import React from 'react';
import moment from 'moment';
import { ClockCircleOutlined } from '@ant-design/icons';



export const RowDateTime = ({format, date }) => {
	if(date){
		return <React.Fragment><ClockCircleOutlined /> {moment(date).format(format)}</React.Fragment>
	}
	return '/'
}

RowDateTime.defaultProps = {
	format:'YYYY-MM-DD H:mm:ss',
	date: new Date
}