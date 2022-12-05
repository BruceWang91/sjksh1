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

const selectDomain = (state: RootState) => state.department || initialState;

export const selectDepartments = createSelector(
  [selectDomain],
  departmentState => departmentState.departments,
);

export const selectDepartmentsAndMembers = createSelector(
  [selectDomain],
  departmentState => departmentState.departmentsAndMembers,
);
export const selectDepartmentsAndMembersLoading = createSelector(
  [selectDomain],
  departmentState => departmentState.departmentsAndMembersLoading,
);

export const selectDepartmentListLoading = createSelector(
  [selectDomain],
  departmentState => departmentState.departmentListLoading,
);

export const selectSaveDepartmentLoading = createSelector(
  [selectDomain],
  departmentState => departmentState.saveDepartmentLoading,
);

export const selectDeleteDepartmentsLoading = createSelector(
  [selectDomain],
  departmentState => departmentState.deleteDepartmentsLoading,
);
