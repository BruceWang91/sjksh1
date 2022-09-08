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
  getSheetData,
  getSheets,
} from './thunks';
import { SheetState } from './types';

export const initialState: SheetState = {
  sheets: [],
  sheetData:{data:[],sheet:{startRows:0,fieldList:[]}},
  totalRecords:1,
  sheetListLoading: false,
  sheetDataLoading: false
};


const slice = createSlice({
  name: 'sheet',
  initialState,
  reducers: {},
  extraReducers: builder => {
    // getSheets
    builder.addCase(getSheets.pending, state => {
      state.sheetListLoading = true;
    });
    builder.addCase(getSheets.fulfilled, (state, action) => {
      state.sheetListLoading = false;
      state.sheets = action.payload.data;
      state.totalRecords = action.payload.total || 30;
    });
    builder.addCase(getSheets.rejected, state => {
      state.sheetListLoading = false;
    });

    builder.addCase(getSheetData.pending, state => {
      state.sheetDataLoading = true;
    });
    builder.addCase(getSheetData.fulfilled, (state, action) => {
      state.sheetDataLoading = false;
      state.sheetData = action.payload;
    });
    builder.addCase(getSheetData.rejected, state => {
      state.sheetDataLoading = false;
    });
  },
});

export const { actions: sheetActions, reducer } = slice;

export const useSheetSlice = () => {
  useInjectReducer({ key: slice.name, reducer: slice.reducer });
  return { actions: slice.actions };
};
