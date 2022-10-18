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
  addDepartment,
  deleteDepartment,
  editDepartment,
  getDepartments,
  getDepartmentsAndMembers,
} from './thunks';
import { DepartmentState } from './types';

export const initialState: DepartmentState = {
  departments: [],
  departmentsAndMembers:[],
  departmentListLoading: false,
  saveDepartmentLoading: false,
  deleteDepartmentsLoading: false,
  departmentsAndMembersLoading:false
};


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
const slice = createSlice({
  name: 'department',
  initialState,
  reducers: {},
  extraReducers: builder => {

  	// getDepartmentsAndMembers
    builder.addCase(getDepartmentsAndMembers.pending, state => {
      state.departmentsAndMembersLoading = true;
    });
    builder.addCase(getDepartmentsAndMembers.fulfilled, (state, action) => {
      state.departmentsAndMembersLoading = false;
      state.departments = treeTs(action.payload) 
    });
    builder.addCase(getDepartmentsAndMembers.rejected, state => {
      state.departmentsAndMembersLoading = false;
    });


    // getDepartments
    builder.addCase(getDepartments.pending, state => {
      state.departmentListLoading = true;
    });
    builder.addCase(getDepartments.fulfilled, (state, action) => {
      state.departmentListLoading = false;
      state.departments = treeTs(action.payload) 
    });
    builder.addCase(getDepartments.rejected, state => {
      state.departmentListLoading = false;
    });

    // addDepartment
    builder.addCase(addDepartment.pending, state => {
      state.saveDepartmentLoading = true;
    });
    builder.addCase(addDepartment.fulfilled, (state, action) => {
      state.saveDepartmentLoading = false;
      console.log(action.payload)
      //state.departments.push({ ...action.payload, deleteLoading: false });
    });
    builder.addCase(addDepartment.rejected, state => {
      state.saveDepartmentLoading = false;
    });

    // editDepartment
    builder.addCase(editDepartment.pending, state => {
      state.saveDepartmentLoading = true;
    });
    builder.addCase(editDepartment.fulfilled, (state, action) => {
      state.saveDepartmentLoading = false;
      /*state.departments = state.departments.map(v =>
        v.deptId === action.payload.deptId
          ? { ...action.payload, deleteLoading: false }
          : v,
      );*/
    });
    builder.addCase(editDepartment.rejected, state => {
      state.saveDepartmentLoading = false;
    });

    // deleteDepartment
    builder.addCase(deleteDepartment.pending, (state, action) => {
      /*if (action.meta.arg.ids.length === 1) {
        const department = state.departments.find(
          ({ id }) => id === action.meta.arg.ids[0],
        );
        if (department) {
          department.deleteLoading = true;
        }
      } else {
        
      }*/

      state.deleteDepartmentsLoading = true;
    });
    builder.addCase(deleteDepartment.fulfilled, (state, action) => {
      state.deleteDepartmentsLoading = false;
      /*state.departments = state.departments.filter(
        ({ id }) => !action.meta.arg.ids.includes(id),
      );*/
    });
    builder.addCase(deleteDepartment.rejected, (state, action) => {
      /*if (action.meta.arg.ids.length === 1) {
        const department = state.departments.find(
          ({ id }) => id === action.meta.arg.ids[0],
        );
        if (department) {
          department.deleteLoading = false;
        }
      } else {
        
      }*/

      state.deleteDepartmentsLoading = false;
    });
  },
});

export const { actions: departmentActions, reducer } = slice;

export const useDepartmentSlice = () => {
  useInjectReducer({ key: slice.name, reducer: slice.reducer });
  return { actions: slice.actions };
};
