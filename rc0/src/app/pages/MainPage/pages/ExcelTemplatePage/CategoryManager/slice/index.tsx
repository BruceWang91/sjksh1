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
  addCategory,
  deleteCategory,
  editCategory,
  getCategories,
} from './thunks';
import { CategoryState } from './types';

export const initialState: CategoryState = {
  categories: [],
  categoryListLoading: false,
  saveCategoryLoading: false,
  deleteCategoriesLoading: false,
};


const treeTs = (t) => {
	return t?.map(d=>{
		if(d?.lowClasses?.length > 0){
			d.children = treeTs(d.lowClasses);
		}else{
			delete d.children
		}
		const leaf = d?.children?.length ?? 0;
		return {...d,label:d.name,value:d.id,displayValue:d.name,hideValue:d.id,leaf,isLeaf:leaf === 0 }
	})
}
const slice = createSlice({
  name: 'category',
  initialState,
  reducers: {},
  extraReducers: builder => {
    // getCategories
    builder.addCase(getCategories.pending, state => {
      state.categoryListLoading = true;
    });
    builder.addCase(getCategories.fulfilled, (state, action) => {
      state.categoryListLoading = false;
      state.categories = treeTs(action.payload) 
    });
    builder.addCase(getCategories.rejected, state => {
      state.categoryListLoading = false;
    });

    // addCategory
    builder.addCase(addCategory.pending, state => {
      state.saveCategoryLoading = true;
    });
    builder.addCase(addCategory.fulfilled, (state, action) => {
      state.saveCategoryLoading = false;
      //console.log(action.payload)
      //state.categories.push({ ...action.payload, deleteLoading: false });
    });
    builder.addCase(addCategory.rejected, state => {
      state.saveCategoryLoading = false;
    });

    // editCategory
    builder.addCase(editCategory.pending, state => {
      state.saveCategoryLoading = true;
    });
    builder.addCase(editCategory.fulfilled, (state, action) => {
      state.saveCategoryLoading = false;
      /*state.categories = state.categories.map(v =>
        v.deptId === action.payload.deptId
          ? { ...action.payload, deleteLoading: false }
          : v,
      );*/
    });
    builder.addCase(editCategory.rejected, state => {
      state.saveCategoryLoading = false;
    });

    // deleteCategory
    builder.addCase(deleteCategory.pending, (state, action) => {
      /*if (action.meta.arg.ids.length === 1) {
        const category = state.categories.find(
          ({ id }) => id === action.meta.arg.ids[0],
        );
        if (category) {
          category.deleteLoading = true;
        }
      } else {
        
      }*/

      state.deleteCategoriesLoading = true;
    });
    builder.addCase(deleteCategory.fulfilled, (state, action) => {
      state.deleteCategoriesLoading = false;
      /*state.categories = state.categories.filter(
        ({ id }) => !action.meta.arg.ids.includes(id),
      );*/
    });
    builder.addCase(deleteCategory.rejected, (state, action) => {
      /*if (action.meta.arg.ids.length === 1) {
        const category = state.categories.find(
          ({ id }) => id === action.meta.arg.ids[0],
        );
        if (category) {
          category.deleteLoading = false;
        }
      } else {
        
      }*/

      state.deleteCategoriesLoading = false;
    });
  },
});

export const { actions: categoryActions, reducer } = slice;

export const useCategorySlice = () => {
  useInjectReducer({ key: slice.name, reducer: slice.reducer });
  return { actions: slice.actions };
};
