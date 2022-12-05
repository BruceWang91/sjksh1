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
import capitalize from 'lodash/capitalize';

import {
  ResourceTypes,
  SubjectTypes,
  Viewpoints,
  VizResourceSubTypes,
} from '../constants';
import { generateRootNode, getDefaultPermissionArray } from '../utils';
import {
  getResourcePermission,
  getSubjectPermission,
  grantPermissions,
} from './thunks';
import {
  PermissionState,
  ResourcePermissions,
  SubjectPermissions,
} from './types';

export const initialState: PermissionState = {
  dashboardFolders: void 0,
  datachartFolders: void 0,
  storyboards: void 0,
  views: void 0,
  sources: void 0,
  schedules: void 0,
  roles: void 0,
  members: void 0,
  filemains:void 0,
  files:void 0,
  sheets:void 0,
  reports:void 0,

  middleTables:void 0,
	middleTableListLoading: false,
	immassets:void 0,
	immassetListLoading: false,

  filemainListLoading:void 0,
  fileListLoading:void 0,
  sheetListLoading:void 0,
  reportListLoading:void 0,

  folderListLoading: false,


  storyboardListLoading: false,
  viewListLoading: false,
  sourceListLoading: false,
  scheduleListLoading: false,
  roleListLoading: false,
  memberListLoading: false,
  permissions: {
    [Viewpoints.Subject]: {
      loading: false,
      permissionObject: void 0,
    },
    [Viewpoints.Resource]: {
      loading: false,
      permissionObject: void 0,
    },
  },
};



const slice = createSlice({
  name: 'permission',
  initialState,
  reducers: {},
  extraReducers: builder => {

  	// getFilemains
    builder.addCase(getFilemains.pending, state => {
      state.filemainListLoading = true;
    });
    builder.addCase(getFilemains.fulfilled, (state, action) => {
     

      const root = generateRootNode(
        ResourceTypes.ExcelTemplate,
        null,
      );
      state.filemainListLoading = false;
      state.filemains = [root].concat(
        action.payload?.data?.map(({ fileId, fileName, parentId, index, isFolder  }) => ({
          id:fileId,
          name:fileName,
          type: root.type,
          parentId: parentId === null ? root.id : parentId,
          index,
          isFolder: +isFolder,
        
          permissionArray: getDefaultPermissionArray(),
        })),
      );
    });
    builder.addCase(getFilemains.rejected, state => {
      state.filemainListLoading = false;
    });



    // getMiddleTables2
    builder.addCase(getMiddleTables2.pending, state => {
      state.middleTableListLoading = true;
    });
    builder.addCase(getMiddleTables2.fulfilled, (state, action) => {
     

      const root = generateRootNode(
        ResourceTypes.ImmData,
        null,
      );
      state.middleTableListLoading = false;
      state.middleTables = [root].concat(
        action.payload?.map(({ id, name, parentId, index, isFolder  }) => ({
          id:id,
          name:name,
          type: root.type,
          parentId: (parentId === null || parentId === 0 ) ? root.id : parentId,
          index,
          isFolder: +isFolder,
        
          permissionArray: getDefaultPermissionArray(),
        })),
      );
    });
    builder.addCase(getMiddleTables2.rejected, state => {
      state.middleTableListLoading = false;
    });



    // getImmassets2
    builder.addCase(getImmassets2.pending, state => {
      state.immassetListLoading = true;
    });
    builder.addCase(getImmassets2.fulfilled, (state, action) => {
     

      const root = generateRootNode(
        ResourceTypes.ImmAsset,
        null,
      );
      state.immassetListLoading = false;
      state.immassets = [root].concat(
        action.payload?.map(({ id, moduleName, parentId, index, isFolder  }) => ({
          id:id,
          name:moduleName,
          type: root.type,
          parentId: (parentId === null || parentId === 0 ) ? root.id : parentId,
          index,
          isFolder: +isFolder,
        
          permissionArray: getDefaultPermissionArray(),
        })),
      );
    });
    builder.addCase(getImmassets2.rejected, state => {
      state.immassetListLoading = false;
    });


    // getSheets
    builder.addCase(getSheets.pending, state => {
      state.sheetListLoading = true;
    });
    builder.addCase(getSheets.fulfilled, (state, action) => {
      state.sheetListLoading = false;
      state.sheets = action.payload?.data?.map(({ sheetId,  sheetName }) => ({
        id: sheetId,
        name: sheetName,
        type: ResourceTypes.ExcelView,
        parentId: null,
        index: null,
        isFolder: false,
        permissionArray: getDefaultPermissionArray(),
      }));


    });
    builder.addCase(getSheets.rejected, state => {
      state.sheetListLoading = false;
    });

    // getFiles
    builder.addCase(getFiles.pending, state => {
      state.fileListLoading = true;
    });
    builder.addCase(getFiles.fulfilled, (state, action) => {
      const root = generateRootNode(
        ResourceTypes.File,
        null,
      );
      state.fileListLoading = false;
      state.files = [root].concat(
        action.payload?.data?.map(({ id, fileName, parentId, index, isFolder  }) => ({
          id,
          name:fileName,
          type: root.type,
          parentId: parentId === null ? root.id : parentId,
          index,
          isFolder: +isFolder,
        
          permissionArray: getDefaultPermissionArray(),
        })),
      );
    });
    builder.addCase(getFiles.rejected, state => {
      state.fileListLoading = false;
    });


    // getReports
    builder.addCase(getReports.pending, state => {
      state.reportListLoading = true;
    });
    builder.addCase(getReports.fulfilled, (state, action) => {
      const root = generateRootNode(
        ResourceTypes.Report,
        null,
      );
      state.reportListLoading = false;
      state.reports = [root].concat(
        action.payload?.data?.map(({ id, name, parentId, index, isFolder  }) => ({
          id,
          name,
          type: root.type,
          parentId: parentId === null ? root.id : parentId,
          index,
          isFolder: +isFolder,
        
          permissionArray: getDefaultPermissionArray(),
        })),
      );
      
    });
    builder.addCase(getReports.rejected, state => {
      state.reportListLoading = false;
    });


    // getMembers
    builder.addCase(getMembers.pending, state => {
      state.memberListLoading = true;
    });
    builder.addCase(getMembers.fulfilled, (state, action) => {
      state.memberListLoading = false;
      state.members = action.payload?.map(({ id, username, name }) => ({
        id,
        name: name ? `${name}(${username})` : username,
        type: SubjectTypes.UserRole,
        parentId: null,
        index: null,
        isFolder: false,
        permissionArray: getDefaultPermissionArray(),
      }));
    });
    builder.addCase(getMembers.rejected, state => {
      state.memberListLoading = false;
    });

    // getRoles
    builder.addCase(getRoles.pending, state => {
      state.roleListLoading = true;
    });
    builder.addCase(getRoles.fulfilled, (state, action) => {
      state.roleListLoading = false;
      state.roles = action.payload?.map(({ id, name }) => ({
        id,
        name,
        type: SubjectTypes.Role,
        parentId: null,
        index: null,
        isFolder: false,
        permissionArray: getDefaultPermissionArray(),
      }));
    });
    builder.addCase(getRoles.rejected, state => {
      state.roleListLoading = false;
    });

    // getSchedules
    builder.addCase(getSchedules.pending, state => {
      state.scheduleListLoading = true;
    });
    builder.addCase(getSchedules.fulfilled, (state, action) => {
      state.scheduleListLoading = false;
      state.schedules = action.payload?.map(({ id, name }) => ({
        id,
        name,
        type: ResourceTypes.Schedule,
        parentId: null,
        index: null,
        isFolder: false,
        permissionArray: getDefaultPermissionArray(),
      }));
    });
    builder.addCase(getSchedules.rejected, state => {
      state.scheduleListLoading = false;
    });

    // getSources
    builder.addCase(getSources.pending, state => {
      state.sourceListLoading = true;
    });
    builder.addCase(getSources.fulfilled, (state, action) => {
      state.sourceListLoading = false;
      state.sources = action.payload?.map(({ id, name }) => ({
        id,
        name,
        type: ResourceTypes.Source,
        parentId: null,
        index: null,
        isFolder: false,
        permissionArray: getDefaultPermissionArray(),
      }));
    });
    builder.addCase(getSources.rejected, state => {
      state.sourceListLoading = false;
    });

    // getViews
    builder.addCase(getViews.pending, state => {
      state.viewListLoading = true;
    });
    builder.addCase(getViews.fulfilled, (state, action) => {
      const root = generateRootNode(ResourceTypes.View);
      state.viewListLoading = false;
      state.views = [root].concat(
        action.payload?.map(({ id, name, parentId, index, isFolder }) => ({
          id,
          name,
          type: root.type,
          parentId: parentId === null ? root.id : parentId,
          index,
          isFolder,
          permissionArray: getDefaultPermissionArray(),
        })),
      );
    });
    builder.addCase(getViews.rejected, state => {
      state.viewListLoading = false;
    });

    // getFolders2
    builder.addCase(getFolders2.pending, state => {
      state.folderListLoading = true;
    });
    builder.addCase(getFolders2.fulfilled, (state, action) => {
    	const {data,vizDatatype} = action.payload;
    	console.log(`${vizDatatype.toLowerCase()}Folders`)
      const root = generateRootNode(
        `VIZ_${vizDatatype}`,
        VizResourceSubTypes[`${capitalize(vizDatatype)}Folder`],
      );
      state.folderListLoading = false;


      state[`${vizDatatype.toLowerCase()}Folders`] = [root].concat(
        data?.map(({ id, name, parentId, index, relType, relId }) => ({
          id,
          name,
          type: root.type,
          parentId: parentId === null ? root.id : parentId,
          index,
          isFolder: ['DATACHART_FOLDER','DASHBOARD_FOLDER'].includes(relType),
          relType: relType,
          relId: relId,
          permissionArray: getDefaultPermissionArray(),
        })),
      );
    });
    builder.addCase(getFolders2.rejected, state => {
      state.folderListLoading = false;
    });

    // getStoryboards
    builder.addCase(getStoryboards.pending, state => {
      state.storyboardListLoading = true;
    });
    builder.addCase(getStoryboards.fulfilled, (state, action) => {
      state.storyboardListLoading = false;
      state.storyboards = action.payload?.map(({ id, name }) => ({
        id,
        name,
        type: ResourceTypes.Viz,
        parentId: null,
        index: null,
        isFolder: false,
        permissionArray: getDefaultPermissionArray(),
      }));
    });
    builder.addCase(getStoryboards.rejected, state => {
      state.storyboardListLoading = false;
    });

    // getResourcePermission
    builder.addCase(getResourcePermission.pending, state => {
      state.permissions[Viewpoints.Resource].loading = true;
    });
    builder.addCase(getResourcePermission.fulfilled, (state, action) => {
      state.permissions[Viewpoints.Resource].loading = false;
      state.permissions[Viewpoints.Resource].permissionObject = action.payload;
    });
    builder.addCase(getResourcePermission.rejected, state => {
      state.permissions[Viewpoints.Resource].loading = false;
    });

    // getSubjectPermission
    builder.addCase(getSubjectPermission.pending, state => {
      state.permissions[Viewpoints.Subject].loading = true;
    });
    builder.addCase(getSubjectPermission.fulfilled, (state, action) => {
      state.permissions[Viewpoints.Subject].loading = false;
      state.permissions[Viewpoints.Subject].permissionObject = action.payload;
    });
    builder.addCase(getSubjectPermission.rejected, state => {
      state.permissions[Viewpoints.Subject].loading = false;
    });

    // grantPermissions
    builder.addCase(grantPermissions.pending, state => {});
    builder.addCase(grantPermissions.fulfilled, (state, action) => {
      const { viewpoint, viewpointType, dataSourceType } =
        action.meta.arg.options;
      if (viewpoint === Viewpoints.Resource) {
        const permissionObject = state.permissions[viewpoint]
          .permissionObject as ResourcePermissions;
        if (dataSourceType === SubjectTypes.Role) {
          permissionObject.rolePermissions = permissionObject.rolePermissions
            .filter((x) => x?.resourceType !== viewpointType)
            .concat(action.payload);
        } else {
          permissionObject.userPermissions = permissionObject.userPermissions
            .filter((x) => x?.resourceType !== viewpointType)
            .concat(action.payload);
        }
      } else {
        const permissionObject = state.permissions[viewpoint]
          .permissionObject as SubjectPermissions;
        permissionObject.permissionInfos = permissionObject.permissionInfos
          .filter((x) => x?.resourceType !== dataSourceType)
          .concat(action.payload);
      }
    });
    builder.addCase(grantPermissions.rejected, state => {});
  },
});

export const { actions: permissionActions, reducer } = slice;

export const usePermissionSlice = () => {
  useInjectReducer({ key: slice.name, reducer: slice.reducer });
  return { actions: slice.actions };
};
