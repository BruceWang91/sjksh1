/**
 * Seas
 *
 * Copyright 2021
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import { createAsyncThunk } from '@reduxjs/toolkit';
import { request2 } from 'utils/request';
import {
  Report,
} from './types';

export const getReports = createAsyncThunk<Report[], string>(
  'report/getReports',
  async ( qs ) => {
    const { rows , total} = await request2<Report[]>({
      url: 'jmreportrewrite/getlist',
      method: 'GET',
      params: qs,
    });
    return {data:rows,total};
  },
);

export const getReportData = createAsyncThunk<any[], string>(
  'report/getReportData',
  async ({orgId,reportId,resolve}) => {

  	const reportResult = await request2<Report[]>({
      url: '/file/filemain/selectForReportId/'+reportId,
      method: 'GET',
      params: {},
    });


    const reportDataResult = await request2<Report[]>({
      url: '/file/filemain/getReportData/'+reportId,
      method: 'GET',
      params: {},
    });
    const result  = {report:reportResult.data,data:reportDataResult.data};
    resolve && resolve(result)
    return result
  },
);


export const initReport = createAsyncThunk<Report, string>(
  'report/initReport',
  async ({ report, resolve }) => {
    const { result } = await request2<Report>({
    	baseURL:'',
      url: '/jmreport/save',
      method: 'POST',
      data: report,
    });
    resolve && resolve(result);
    return result;
  },
);

export const saveReport = createAsyncThunk<Report, string>(
  'report/saveReport',
  async ({ report, resolve }) => {
    const { result } = await request2<Report>({
    	baseURL:'/jmreport',
      url: '/excelQueryName',
      method: 'POST',
      data: report,
    });
    resolve && resolve(result);
    return result;
  },
);

