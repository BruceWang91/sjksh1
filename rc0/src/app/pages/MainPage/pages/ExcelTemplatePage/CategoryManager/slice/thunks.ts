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
  AddCategoryParams,
  DeleteCategoryParams,
  EditCategoryParams,
  Category,
} from './types';
const treeTs = (t) => {
	return t.map(d=>{
		if(d?.lowClasses?.length > 0){
			d.children = treeTs(d.lowClasses);
		}else{
			delete d.children
		}
		const leaf = d?.children?.length ?? 0;
		return {...d,label:d.name,value:d.id,displayValue:d.name,hideValue:d.id,leaf,isLeaf:leaf === 0 }
	})
}
export const getCategories = createAsyncThunk<Category[], string>(
  'category/getCategories',
  async ({orgId,parentId , resolve}) => {
    const { rows,data } = await request2<Category[]>({
      url: '/file/class/list',
      method: 'GET',
      params: { parentId},
    });

    resolve && resolve(treeTs(rows || data))
    return rows || data;
  },
);
export const addCategory = createAsyncThunk<Category, AddCategoryParams>(
  'category/addCategory',
  async ({ category, resolve }) => {

    const { data } = await request2<Category>({
      url: '/file/class/add',
      method: 'POST',
      data: category,
    });
    resolve();
    return data;
  },
);

export const editCategory = createAsyncThunk<Category, EditCategoryParams>(
  'category/editCategory',
  async ({ category, resolve }) => {
    await request2<boolean>({
      url: '/file/class/edit',
      method: 'PUT',
      data: category,
    });
    resolve();
    return category;
  },
);

export const deleteCategory = createAsyncThunk<null, DeleteCategoryParams>(
  'category/deleteCategory',
  async ({ ids, resolve }) => {
    await request2<Category>({
      url: '/file/class/remove/'+ids.join(','),
      method: 'DELETE',
      params: {},
    });
    resolve();
    return null;
  },
);
