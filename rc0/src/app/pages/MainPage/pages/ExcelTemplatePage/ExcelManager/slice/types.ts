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
import { FilemainTypes, FilemainValueTypes } from '../constants';



export interface FilemainState {
  filemains: FilemainViewModel[];
  filemainListLoading: boolean;
  saveFilemainLoading: boolean;
  deleteFilemainsLoading: boolean;
}

export interface Filemain {
	classId: number;
  classIds: number[];
  createBy: string;
  createTime: string;
  delFlag: string;
  depIds: number[];
  fileId: number;
  fileName: string;
  orderNum: number;
  remark: string;
  status: string;
  updateBy: string;
  updateTime: string;

}
			


export interface FilemainViewModel extends Filemain {
  deleteLoading: boolean;
  key:string
}
export interface FilemainBa extends Filemain {
  sheets:any[];
}

export interface importFilemainParams {
  filemain: Omit<FilemainBa, 'fileId'>;
  resolve: () => void;
}

export interface importFilemainDataParams {
  file: File;
  fileId:string;
  resolve: () => void;
}


export interface getFilemainParams {
  fileId: string;
  resolve: () => void;
}
export interface EditFilemainParams {
  filemain: Filemain;
  resolve: () => void;
}

export interface DeleteFilemainParams {
  id: string;
  resolve: () => void;
}
