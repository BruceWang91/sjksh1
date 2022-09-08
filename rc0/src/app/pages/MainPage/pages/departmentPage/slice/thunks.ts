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
  AddDepartmentParams,
  DeleteDepartmentParams,
  EditDepartmentParams,
  Department,
} from './types';
const treeTs = (t) => {
	return t.map(d=>{
		if(d?.children?.length > 0){
			d.children = treeTs(d.children);
		}else{
			delete d.children
		}
		const leaf = d?.children?.length ?? 0;
		return {...d,label:d.deptName,value:d.deptId,displayValue:d.deptName,hideValue:d.deptId,leaf,isLeaf:leaf === 0}
	})
}
export const getDepartments = createAsyncThunk<Department[], string>(
  'department/getDepartments',
  async ({orgId,parentId,resolve}) => {
    const { data } = await request2<Department[]>({
      url: '/system/dept/treeselect',
      method: 'GET',
      params: { parentId},
    });
    resolve && resolve(treeTs(data) )
    return data;
  },
);
export const addDepartment = createAsyncThunk<Department, AddDepartmentParams>(
  'department/addDepartment',
  async ({ department, resolve }) => {

    const { data } = await request2<Department>({
      url: '/system/dept/add',
      method: 'POST',
      data: department,
    });
    resolve();
    return data;
  },
);

export const editDepartment = createAsyncThunk<Department, EditDepartmentParams>(
  'department/editDepartment',
  async ({ department, resolve }) => {
    await request2<boolean>({
      url: '/system/dept/edit',
      method: 'PUT',
      data: department,
    });
    resolve();
    return department;
  },
);

export const deleteDepartment = createAsyncThunk<null, DeleteDepartmentParams>(
  'department/deleteDepartment',
  async ({ id, resolve }) => {
    await request2<Department>({
      url: '/system/dept/'+id,
      method: 'DELETE',
      params: {},
    });
    resolve();
    return null;
  },
);
