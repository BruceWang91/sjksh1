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
  addFilemain,
  deleteFilemain,
  editFilemain,
  getFilemains,
  importFilemain,
  getFilemain,
  getCategories,
  importFilemainData
} from './thunks';
import { FilemainState } from './types';

export const initialState: FilemainState = {
  filemains: [],
  totalRecords:1,
  filemain: null,
  filemainListLoading: false,
  saveFilemainLoading: false,
  deleteFilemainsLoading: false,
  filemainLoading:false,
  importDataLoading:false
};


const slice = createSlice({
  name: 'filemain',
  initialState,
  reducers: {},
  extraReducers: builder => {

  	// importFilemainData
    builder.addCase(importFilemainData.pending, state => {
      state.importDataLoading = true;
    });
    builder.addCase(importFilemainData.fulfilled, (state, action) => {

      state.importDataLoading = false;

    });
    builder.addCase(importFilemainData.rejected, state => {
      state.importDataLoading = false;
    });


    // getFilemains
    builder.addCase(getFilemains.pending, state => {
      state.filemainListLoading = true;
    });
    builder.addCase(getFilemains.fulfilled, (state, action) => {
      state.filemainListLoading = false;
      state.filemains = action.payload.data
      state.totalRecords = action.payload.total || 50
    });
    builder.addCase(getFilemains.rejected, state => {
      state.filemainListLoading = false;
    });


    // getFilemain
    builder.addCase(getFilemain.pending, state => {
      state.filemainLoading = true;
    });
    builder.addCase(getFilemain.fulfilled, (state, action) => {

      state.filemainLoading = false;
      state.filemain = action.payload

    });
    builder.addCase(getFilemain.rejected, state => {
      state.filemainLoading = false;
    });



    // importFilemain
    builder.addCase(importFilemain.pending, state => {
      state.saveFilemainLoading = true;
    });
    builder.addCase(importFilemain.fulfilled, (state, action) => {
      state.saveFilemainLoading = false;
      //state.filemains.push({ ...action.payload, deleteLoading: false });
    });
    builder.addCase(importFilemain.rejected, state => {
      state.saveFilemainLoading = false;
    });


    // editFilemain
    builder.addCase(editFilemain.pending, state => {
      state.saveFilemainLoading = true;
    });
    builder.addCase(editFilemain.fulfilled, (state, action) => {
      state.saveFilemainLoading = false;
      /*state.filemains = state.filemains.map(v =>
        v.deptId === action.payload.deptId
          ? { ...action.payload, deleteLoading: false }
          : v,
      );*/
    });
    builder.addCase(editFilemain.rejected, state => {
      state.saveFilemainLoading = false;
    });

    // deleteFilemain
    builder.addCase(deleteFilemain.pending, (state, action) => {
      /*if (action.meta.arg.ids.length === 1) {
        const filemain = state.filemains.find(
          ({ id }) => id === action.meta.arg.ids[0],
        );
        if (filemain) {
          filemain.deleteLoading = true;
        }
      } else {
        
      }*/

      state.deleteFilemainsLoading = true;
    });
    builder.addCase(deleteFilemain.fulfilled, (state, action) => {
      state.deleteFilemainsLoading = false;
      /*state.filemains = state.filemains.filter(
        ({ id }) => !action.meta.arg.ids.includes(id),
      );*/
    });
    builder.addCase(deleteFilemain.rejected, (state, action) => {
      /*if (action.meta.arg.ids.length === 1) {
        const filemain = state.filemains.find(
          ({ id }) => id === action.meta.arg.ids[0],
        );
        if (filemain) {
          filemain.deleteLoading = false;
        }
      } else {
        
      }*/

      state.deleteFilemainsLoading = false;
    });
  },
});

export const { actions: filemainActions, reducer } = slice;

export const useFilemainSlice = () => {
  useInjectReducer({ key: slice.name, reducer: slice.reducer });
  return { actions: slice.actions };
};
