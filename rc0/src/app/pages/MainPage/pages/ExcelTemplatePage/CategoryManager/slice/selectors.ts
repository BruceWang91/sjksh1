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

const selectDomain = (state: RootState) => state.category || initialState;

export const selectCategories = createSelector(
  [selectDomain],
  categoryState => categoryState.categories,
);

export const selectCategoryListLoading = createSelector(
  [selectDomain],
  categoryState => categoryState.categoryListLoading,
);

export const selectSaveCategoryLoading = createSelector(
  [selectDomain],
  categoryState => categoryState.saveCategoryLoading,
);

export const selectDeleteCategoriesLoading = createSelector(
  [selectDomain],
  categoryState => categoryState.deleteCategoriesLoading,
);
