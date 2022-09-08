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
import { SheetTypes, SheetValueTypes } from '../constants';

export interface SheetState {
  sheets: SheetViewModel[];
  sheetListLoading: boolean;
}

export interface Sheet {
  ellCount?: number,
  createBy?: string,
  createTime?: string,
  delFlag?: string,
  entityName?: string,
  fileId?: number,
  orderNum?: number,
  remark?: string,
  sheetId: number,
  sheetName?: string,
  startCell?: number,
  startRow?: number,
  status?: string,
  updateBy?: string,
  updateTime?: string
}

export interface SheetViewModel extends Sheet {
  key:string
}


