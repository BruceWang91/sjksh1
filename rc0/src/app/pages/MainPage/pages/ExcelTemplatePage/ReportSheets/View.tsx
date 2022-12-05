import React , {useMemo,useCallback} from 'react';
import 'handsontable/dist/handsontable.full.css';
import { HotTable } from '@handsontable/react';
import {Dialog} from 'app/components/Dialog'; 
import {Excel,ExcelIProps} from '../Excel'; 
import {Empty} from 'antd';

import {readExcel,serverDataconvertToExcel ,listConvertToExcelData,excelConvertToServerData,uuid,exportXLSX} from '../utils';




import { useDispatch, useSelector } from 'react-redux';
import { useSheetSlice } from './slice';
import { selectOrgId } from '../../../slice/selectors';
import {
  selectSheetDataLoading,
  selectSheetData
} from './slice/selectors';
import {
  getSheetData,
} from './slice/thunks';

import styled from 'styled-components/macro';



interface IProps {
	children:React.ReactNode;
	sheetId:string;
	viewPortWidth?:string|number;
	viewPortHeight?:string|number;
	title?:string;
}


export function View ({
	sheetId,
	children,
	viewPortWidth,
	viewPortHeight,
	title,
}:IProps) {

	useSheetSlice();


	const dispatch = useDispatch();
  const orgId = useSelector(selectOrgId);
  const result = useSelector(selectSheetData);
  const loading = useSelector(selectSheetDataLoading);


	const onOpen = useCallback(()=>{
		dispatch(getSheetData({sheetId}))
	},[sheetId])


	const sheet = useMemo(()=>{
		const {startRows,fieldList} = result.sheet ; 
	  const sheet = serverDataconvertToExcel([result.sheet],false)[0] ;
	  sheet.cells = [...sheet.cells,...listConvertToExcelData( result?.data?.[0],fieldList)]
		return sheet
	},[sheetId,result])


	//console.log(sheets)

	const excel = <Wrap>
		{sheet.cells.length === 0 && !loading && <Empty message="暂无数据"  />}
		{sheet.cells.length > 0 && !loading && <Excel 
      	columns={sheet.columns} 
				colHeaders={sheet.colHeaders} 
				mergeCells={sheet.mergeCells} 
				cellsOption={sheet.cellsOption} 
				cells={sheet.cells}
				viewPortWidth={viewPortWidth}
				viewPortHeight={viewPortHeight}
			/>}
	</Wrap>
	return <Dialog title={title} loading={loading} footer={false} width="80%"  render={excel} onOpen={onOpen} >{children}</Dialog>
}


View.defaultProps = {
	viewPortHeight:'auto'
}



const Wrap = styled.div`
	
	
	min-height:100px;

	.ant-tabs-nav{
		margin-bottom:0;
		height:32px;
	}

	 .ant-tabs-tab{
	 	padding:0 12px !important;
	 }


`