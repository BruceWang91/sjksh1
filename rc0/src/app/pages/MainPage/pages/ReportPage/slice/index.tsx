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

import { createSlice } from '@reduxjs/toolkit';
import { useInjectReducer } from 'utils/@reduxjs/injectReducer';
import {
  getReportData,
  getReports,
  saveReport,
  initReport
} from './thunks';
import { ReportState } from './types';

export const initialState: ReportState = {
  reports: [],
  reportData:{data:[],report:{startRows:0,fieldList:[]}},
  totalRecords:1,
  reportListLoading: false,
  reportDataLoading: false
};


const slice = createSlice({
  name: 'report',
  initialState,
  reducers: {},
  extraReducers: builder => {
    // getReports
    builder.addCase(getReports.pending, state => {
      state.reportListLoading = true;
    });
    builder.addCase(getReports.fulfilled, (state, action) => {
      state.reportListLoading = false;
      state.reports = action.payload.data;
      state.totalRecords = action.payload.total || 30;
    });
    builder.addCase(getReports.rejected, state => {
      state.reportListLoading = false;
    });

    builder.addCase(getReportData.pending, state => {
      state.reportDataLoading = true;
    });
    builder.addCase(getReportData.fulfilled, (state, action) => {
      state.reportDataLoading = false;
      state.reportData = action.payload;
    });
    builder.addCase(getReportData.rejected, state => {
      state.reportDataLoading = false;
    });

     builder.addCase(saveReport.pending, state => {
      state.saveReportLoading = true;
    });
    builder.addCase(saveReport.fulfilled, (state, action) => {
      state.saveReportLoading = false;
    });
    builder.addCase(saveReport.rejected, state => {
      state.saveReportLoading = false;
    });

     builder.addCase(initReport.pending, state => {
      state.saveReportLoading = true;
    });
    builder.addCase(initReport.fulfilled, (state, action) => {
      state.saveReportLoading = false;
    });
    builder.addCase(initReport.rejected, state => {
      state.saveReportLoading = false;
    });
  },
});

export const { actions: reportActions, reducer } = slice;

export const useReportSlice = () => {
  useInjectReducer({ key: slice.name, reducer: slice.reducer });
  return { actions: slice.actions };
};
