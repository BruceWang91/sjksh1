import React , {useMemo,useCallback} from 'react';
import 'handsontable/dist/handsontable.full.css';
import { HotTable } from '@handsontable/react';
import {Dialog} from 'app/components/Dialog'; 
import {Excel,ExcelIProps} from './Excel'; 
import {Tabs,Empty} from 'antd';

import {readExcel,serverDataconvertToExcel ,excelConvertToServerData,uuid,exportXLSX} from './utils';




import { useDispatch, useSelector } from 'react-redux';
import { useFilemainSlice } from './ExcelManager/slice';
import { selectOrgId } from '../../slice/selectors';
import {
  selectFilemainLoading,
  selectFilemain,
} from './ExcelManager/slice/selectors';
import {
  getFilemain,
} from './ExcelManager/slice/thunks';

import styled from 'styled-components/macro';



interface IProps {
	children:React.ReactNode;
	fileId:string;
	viewPortWidth?:string|number;
	viewPortHeight?:string|number;
	title?:string;
}


export function Browser ({
	fileId,
	children,
	viewPortWidth,
	viewPortHeight,
	title,
}:IProps) {

	useFilemainSlice();


	const dispatch = useDispatch();
  const orgId = useSelector(selectOrgId);
  const result = useSelector(selectFilemain);
  const loading = useSelector(selectFilemainLoading);


	const onOpen = useCallback(()=>{
		dispatch(getFilemain({fileId}))
	},[fileId])


	const sheets = useMemo(()=>{
		return serverDataconvertToExcel(result?.sheets )
	},[fileId,result])


	//console.log(sheets)

	const excel = <Wrap>
		{sheets.length === 0 && !loading && <Empty message="未上传表样"  />}
		{sheets.length > 0 && (<Tabs type="card" >
      {!loading && sheets.map((sheet,key) => (
        <Tabs.TabPane tab={sheet.sheetName} key={key}>
          <Excel 
          	columns={sheet.columns} 
						colHeaders={sheet.colHeaders} 
						mergeCells={sheet.mergeCells} 
						cellsOption={sheet.cellsOption} 
						cells={sheet.cells}
						viewPortWidth={viewPortWidth}
						viewPortHeight={viewPortHeight}
					/>
        </Tabs.TabPane>
      ))}
    </Tabs>)}
	</Wrap>
	return <Dialog title={title} loading={loading} footer={false} width="80%"  render={excel} onOpen={onOpen} >{children}</Dialog>
}


Browser.defaultProps = {
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