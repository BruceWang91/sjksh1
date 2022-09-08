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
  Sheet,
} from './types';

export const getSheets = createAsyncThunk<Sheet[], string>(
  'sheet/getSheets',
  async ( qs ) => {
    const { rows , total} = await request2<Sheet[]>({
      url: '/file/sheets/list',
      method: 'GET',
      params: qs,
    });
    return {data:rows,total};
  },
);

export const getSheetData = createAsyncThunk<any[], string>(
  'sheet/getSheetData',
  async ({orgId,sheetId,resolve}) => {

  	const sheetResult = await request2<Sheet[]>({
      url: '/file/filemain/selectForSheetId/'+sheetId,
      method: 'GET',
      params: {},
    });


    const sheetDataResult = await request2<Sheet[]>({
      url: '/file/filemain/getSheetData/'+sheetId,
      method: 'GET',
      params: {},
    });
    const result  = {sheet:sheetResult.data,data:sheetDataResult.data};
    resolve && resolve(result)
    return result
  },
);
