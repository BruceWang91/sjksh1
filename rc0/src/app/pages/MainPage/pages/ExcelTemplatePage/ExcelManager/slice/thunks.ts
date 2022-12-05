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
  DeleteFilemainParams,
  EditFilemainParams,
  Filemain,
  importFilemainParams,
  getFilemainParams
  
} from './types';


export const getSources = createAsyncThunk(
  'filemain/getSources',
  async ( {resolve,orgId} ) => {
    const { data } = await request2({
      url: '/sources/listSources',
      method: 'GET',
      params: {orgId},
    });
    resolve && resolve(data)
    return data;
  },
);

export const getFilemains2 = createAsyncThunk(
  'filemain/getFilemains2',
  async ( {resolve,...qs} ) => {
    const { rows ,total} = await request2({
      url: '/file/filemain/list',
      method: 'GET',
      params: qs,
    });
    const ret = {data:rows,total};
    resolve && resolve(ret)
    return ret;
  },
);

export const getFilemains = createAsyncThunk(
  'filemain/getFilemains',
  async ( qs ) => {
    const { rows ,total} = await request2({
      url: '/file/filemain/list',
      method: 'GET',
      params: qs,
    });

    return {data:rows,total};
  },
);

export const getFilemain = createAsyncThunk(
  'filemain/getFilemain',
  async ( {fileId , resolve}) => {

    const { data } = await request2({
      url: '/file/filemain/selectForFileId/'+fileId,
      method: 'GET',
      params: {},
    });
    resolve && resolve(data)
    return data;
  },
);


export const importFilemain = createAsyncThunk(
  'filemain/importFilemain',
  async ({ filemain, resolve }) => {
    const { data } = await request2({
      url: '/file/filemain/batchUpdateForFileId',
      method: 'POST',
      data: filemain,
    });
    resolve();
    return data;
  },
);


export const importFilemainData = createAsyncThunk(
  'filemain/importFilemainData',
  async ({ file, fileId, resolve }) => {
  	const fd = new FormData();
  	fd.append('file',file)
    const { data } = await request2({
      url: '/files/importfiledata?sourceId='+fileId,
      method: 'POST',
      data: fd,
    });
    resolve();
    return data;
  },
);


export const addFilemain = createAsyncThunk(
  'filemain/addFilemain',
  async ({ inSider,filemain, resolve }) => {
    const {data} = await request2({
      url: '/file/filemain/addFileMainFolder',
      method: 'POST',
      data: filemain,
    });
    resolve(data);
    return {...data,inSider};
  },
);
export const editFilemain = createAsyncThunk(
  'filemain/editFilemain',
  async ({ filemain, resolve }) => {
    await request2({
      url: '/file/filemain/updateFileMainParentId',
      method: 'POST',
      data: filemain,
    });
    resolve(filemain);
    return filemain;
  },
);

export const deleteFilemain = createAsyncThunk(
  'filemain/deleteFilemain',
  async ({ ids, resolve }) => {
    await request2({
      url: '/file/filemain/remove?ids='+ids.join(','),
      method: 'POST',
      params: {},
    });
    resolve(ids);
    return ids;
  },
);
