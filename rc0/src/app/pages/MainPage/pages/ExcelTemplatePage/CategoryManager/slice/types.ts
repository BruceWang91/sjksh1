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
import { CategoryTypes, CategoryValueTypes } from '../constants';

export interface CategoryState {
  categories: CategoryViewModel[];
  categoryListLoading: boolean;
  saveCategoryLoading: boolean;
  deleteCategoriesLoading: boolean;
}

export interface Category {
	ancestors:string;
  id: string;
  parentId?: string;
  name: string;
  parentName?: string;
  status?: string;
  orderNum?: number;
  createBy?: string;
  createTime?: string;
  updateBy?: string;
  updateTime?: string;
  children:CategoryViewModel[]
}

export interface CategoryViewModel extends Category {
  deleteLoading: boolean;
  key:string
}

export interface AddCategoryParams {
  category: Omit<Category, 'id'>;
  resolve: () => void;
}

export interface EditCategoryParams {
  category: Category;
  resolve: () => void;
}

export interface DeleteCategoryParams {
  id: string;
  resolve: () => void;
}
