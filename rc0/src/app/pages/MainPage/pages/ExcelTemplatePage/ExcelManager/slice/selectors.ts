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

import { createSelector } from '@reduxjs/toolkit';
import { RootState } from 'types';
import { initialState } from '.';

const selectDomain = (state: RootState) => state.filemain || initialState;


export const selectSources = createSelector(
  [selectDomain],
  filemainState => filemainState.sources,
);



export const selectFilemains = createSelector(
  [selectDomain],
  filemainState => filemainState.filemains,
);

export const selectFilemain = createSelector(
  [selectDomain],
  filemainState => filemainState.filemain,
);



export const selectFilemainLoading = createSelector(
  [selectDomain],
  filemainState => filemainState.filemainLoading,
);


export const selectImportDataLoading = createSelector(
  [selectDomain],
  filemainState => filemainState.importDataLoading,
);


export const selectTotalRecords = createSelector(
  [selectDomain],
  filemainState => filemainState.totalRecords,
);



export const selectFilemainListLoading = createSelector(
  [selectDomain],
  filemainState => filemainState.filemainListLoading,
);

export const selectSaveFilemainLoading = createSelector(
  [selectDomain],
  filemainState => filemainState.saveFilemainLoading,
);

export const selectDeleteFilemainsLoading = createSelector(
  [selectDomain],
  filemainState => filemainState.deleteFilemainsLoading,
);
