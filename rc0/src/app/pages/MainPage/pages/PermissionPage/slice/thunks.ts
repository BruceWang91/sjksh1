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
import { selectOrgId } from 'app/pages/MainPage/slice/selectors';
import { RootState } from 'types';
import { request2 } from 'utils/request';
import { getMembers, getRoles } from '../../MemberPage/slice/thunks';
import { getSchedules } from '../../SchedulePage/slice/thunks';
import { getSources } from '../../SourcePage/slice/thunks';
import { getViews } from '../../ViewPage/slice/thunks';
import { getFolders2, getStoryboards } from '../../VizPage/slice/thunks';
import { getFilemains } from '../../ExcelTemplatePage/ExcelManager/slice/thunks';
import { getSheets } from '../../ExcelTemplatePage/ReportSheets/slice/thunks';
import { getFiles } from '../../FilePage/slice/thunks';
import { getReports } from '../../ReportPage/slice/thunks';
import { getMiddleTables2 } from '../../MiddleTablePage/slice/thunks';
import { getImmassets2 } from '../../ImmassetPage/slice/thunks';



import { ResourceTypes, SubjectTypes, Viewpoints } from '../constants';
import {
	selectMiddleTables,
	selectMiddleTableListLoading,
	selectImmassets,
	selectImmassetListLoading,

	selectFilemains,
	selectFilemainListLoading,
	selectFiles,
	selectFileListLoading,
	selectSheets,
	selectSheetListLoading,
	selectReports,
	selectReportListLoading,

  selectFolderListLoading,
  selectDatachartFolders,
  selectDashboardFolders,
  selectMemberListLoading,
  selectMembers,
  selectRoleListLoading,
  selectRoles,
  selectScheduleListLoading,
  selectSchedules,
  selectSourceListLoading,
  selectSources,
  selectStoryboardListLoading,
  selectStoryboards,
  selectViewListLoading,
  selectViews,
} from './selectors';
import {
  GetPermissionParams,
  GrantPermissionParams,
  Privilege,
  ResourcePermissions,
  SubjectPermissions,
} from './types';

export const getResourcePermission = createAsyncThunk<
  ResourcePermissions,
  GetPermissionParams<ResourceTypes>
>('permission/getResourcePermission', async ({ orgId, type, id }) => {
  const { data } = await request2<ResourcePermissions>({
    url: '/roles/permission/resource',
    method: 'GET',
    params: { orgId, resourceType: type, resourceId: id },
  });
  return data;
});

export const getSubjectPermission = createAsyncThunk<
  SubjectPermissions,
  GetPermissionParams<SubjectTypes>
>('permission/getSubjectPermission', async ({ orgId, type, id }) => {
  const { data } = await request2<SubjectPermissions>({
    url: '/roles/permission/subject',
    method: 'GET',
    params: { orgId, subjectType: type, subjectId: id },
  });
  return data;
});

export const getDataSource = createAsyncThunk<
  null,
  { viewpoint: Viewpoints; dataSourceType: ResourceTypes | SubjectTypes },
  { state: RootState }
>(
  'permission/getDataSource',
  async ({ viewpoint, dataSourceType }, { getState, dispatch }) => {
    const datachartFolders = selectDatachartFolders(getState());
    const dashboardFolders = selectDashboardFolders(getState());
    const storyboards = selectStoryboards(getState());
    const views = selectViews(getState());
    const sources = selectSources(getState());
    const schedules = selectSchedules(getState());
    const roles = selectRoles(getState());
    const members = selectMembers(getState());
    const filemains = selectFilemains(getState());
    const files = selectFiles(getState());
    const reports = selectReports(getState());
    const middleTables = selectMiddleTables(getState());
    const immassets = selectImmassets(getState());


    const middleTableLoading = selectMiddleTableListLoading(getState());
    const immassetLoading = selectImmassetListLoading(getState());

    const filemainListLoading = selectFilemainListLoading(getState());
    const fileListLoading = selectFileListLoading(getState());
    const reportListLoading = selectReportListLoading(getState());

    const folderListLoading = selectFolderListLoading(getState());

    const storyboardListLoading = selectStoryboardListLoading(getState());
    const viewListLoading = selectViewListLoading(getState());
    const sourceListLoading = selectSourceListLoading(getState());
    const scheduleListLoading = selectScheduleListLoading(getState());
    const roleListLoading = selectRoleListLoading(getState());
    const memberListLoading = selectMemberListLoading(getState());
    const orgId = selectOrgId(getState());



    switch (dataSourceType) {
      case SubjectTypes.Role:
        if (!roles && !roleListLoading) {
          dispatch(getRoles(orgId));
        }
        break;
      case SubjectTypes.UserRole:
        if (!members && !memberListLoading) {
          dispatch(getMembers(orgId));
        }
        break;
      case ResourceTypes.VizDatachart:
      	
        if (!datachartFolders && !folderListLoading) {
          dispatch(getFolders2({orgId,vizDatatype:'DATACHART'}));
        }
        if (!storyboards && !storyboardListLoading) {
          //dispatch(getStoryboards(orgId));
        }
        break;
      case ResourceTypes.VizDashboard:
        if (!dashboardFolders && !folderListLoading) {
          dispatch(getFolders2({orgId,vizDatatype:'DASHBOARD'}));
        }
        if (!storyboards && !storyboardListLoading) {
          //dispatch(getStoryboards(orgId));
        }
        break;
      case ResourceTypes.View:
        if (!views && !viewListLoading) {
          dispatch(getViews(orgId));
        }
        break;
      case ResourceTypes.Source:
        if (!sources && !sourceListLoading) {
          dispatch(getSources(orgId));
        }
        break;
      case ResourceTypes.ExcelTemplate:
        if (!filemains && !filemainListLoading) {
          dispatch(getFilemains({pageSize:9999,pageNum:1}));
        }
        break;
      case ResourceTypes.Report:
        if (!reports && !reportListLoading) {
          dispatch(getReports({pageSize:9999,pageNum:1}));
        }
        break;
      case ResourceTypes.File:
        if (!files && !fileListLoading) {
          dispatch(getFiles({pageSize:9999,pageNum:1}));
        }
        break;
      case ResourceTypes.ImmAsset:
        if (!immassets && !immassetLoading) {
          dispatch(getImmassets2({}));
        }
        break;
      case ResourceTypes.ImmData:
        if (!middleTables && !middleTableLoading) {
          dispatch(getMiddleTables2({}));
        }
        break;
      case ResourceTypes.Schedule:
        if (!schedules && !scheduleListLoading) {
          dispatch(getSchedules(orgId));
        }
        break;
    }
    return null;
  },
);

export const grantPermissions = createAsyncThunk<
  Privilege[],
  GrantPermissionParams
>('permission/grantPermissions', async ({ params, options, resolve }) => {
  const { data } = await request2<Privilege[]>({
    url: '/roles/permission/grant',
    method: 'POST',
    data: params,
  });
  resolve();
  return options.reserved.concat(data);
});
