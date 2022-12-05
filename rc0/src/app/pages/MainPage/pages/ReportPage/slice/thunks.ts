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
import { getToken } from 'utils/auth';

import {
  Report,
} from './types';
export const getReports2 = createAsyncThunk(
  'report/getReports2',
  async ( {resolve,...qs} ) => {
    const { rows , total} = await request2({
      url: 'jmreportrewrite/getlist',
      method: 'GET',
      params: qs,
    });
    const ret = {data:rows,total};
    resolve && resolve(ret)
    return ret;
  },
);
export const getReports = createAsyncThunk(
  'report/getReports',
  async ( qs ) => {
    const { rows , total} = await request2({
      url: 'jmreportrewrite/getlist',
      method: 'GET',
      params: qs,
    });
    return {data:rows,total};
  },
);

export const getReportData = createAsyncThunk(
  'report/getReportData',
  async ({orgId,reportId,resolve}) => {

  	const reportResult = await request2({
      url: '/file/filemain/selectForReportId/'+reportId,
      method: 'GET',
      params: {},
    });


    const reportDataResult = await request2({
      url: '/file/filemain/getReportData/'+reportId,
      method: 'GET',
      params: {},
    });
    const result  = {report:reportResult.data,data:reportDataResult.data};
    resolve && resolve(result)
    return result
  },
);


export const initReport = createAsyncThunk(
  'report/initReport',
  async ({ report, resolve }) => {
    const { result } = await request2({
    	baseURL:'',
      url: '/jmreportrewrite/save',
      method: 'POST',
      data: report,
    });
    resolve && resolve(result);
    return result;
  },
);

export const saveReport = createAsyncThunk(
  'report/saveReport',
  async ({ inSider,report,resolve }) => {
    const { result } = await request2({
      url: '/jmreportrewrite/save',
      method: 'POST',
      data: {designerObj:report},
    });
    resolve && resolve(result);
    return {...result,inSider};
  },
);


export const updateReport = createAsyncThunk(
  'report/updateReport',
  async ({ report,resolve }) => {
    await request2({
      url: '/jmreportrewrite/updateParentId',
      method: 'POST',
      data: report,
    });
    resolve && resolve(report);
    return report;
  },
);


export const deleteReport = createAsyncThunk(
  'report/deleteReport',
  async ({ ids ,resolve }) => {

    await request2({
      url: '/jmreportrewrite/deleteFolder/'+ids[0],
      method: 'DELETE',
      data: {},
    });
    resolve && resolve(ids);
    return ids;
  },
);


export const removeReport = createAsyncThunk(
  'report/removeReport',
  async ({ ids ,resolve }) => {

    await request2({
    	baseURL:'',
      url: `/jmreport/delete/?id=${ids[0]}&token=${getToken(false)}`,
      method: 'DELETE',
      data: {},
    });
    resolve && resolve(ids);
    return ids;
  },
);
export const copyReport = createAsyncThunk(
  'report/copyReport',
  async ({ id ,resolve }) => {

    await request2({
    	baseURL:'',
      url: `/jmreport/reportCopy`,
      method: 'GET',
      params: {
      	id,
      	token:getToken(false)
      },
    });
    resolve && resolve(id);
    return id;
  },
);
