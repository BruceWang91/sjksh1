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

import { SubjectTypes } from '../../PermissionPage/constants';
import { DepartmentTypes, DepartmentValueTypes } from '../constants';

export interface DepartmentState {
  departments: DepartmentViewModel[];
  departmentListLoading: boolean;
  saveDepartmentLoading: boolean;
  deleteDepartmentsLoading: boolean;
}

export interface Department {
	ancestors:string;
  deptId: string;
  parentId?: string;
  deptName: string;
  parentName?: string;
  status?: string;
  orderNum?: number;
  createBy?: string;
  createTime?: string;
  updateBy?: string;
  updateTime?: string;
  children:DepartmentViewModel[]
}

export interface DepartmentViewModel extends Department {
  deleteLoading: boolean;
  key:string
}

export interface AddDepartmentParams {
  department: Omit<Department, 'deptId'>;
  resolve: () => void;
}

export interface EditDepartmentParams {
  department: Department;
  resolve: () => void;
}

export interface DeleteDepartmentParams {
  id: string;
  resolve: () => void;
}
