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

export enum Viewpoints {
  Subject = 'subject',
  Resource = 'resource',
}

export enum ResourceTypes {
	System = 'SYSTEM',
	Department = 'DEPARTMENT',
	Permission = 'PERMISSION',
	Category = 'CATEGORY',//分类
	Variable = 'VARIABLE',
	ResourceMigration = 'RESOURCE_MIGRATION',


	
	DataAccess='DATA_ACCESS',
	DataImport='DATA_IMPORT',
	ExcelView='EXCEL_VIEW',//表样数据预览
	Report = 'REPORT',//报表
	File = 'FILE',//文件上传
	ExcelTemplate = 'EXCEL_TEMPLATE',//表样管理
  Source = 'SOURCE',
  View = 'VIEW',
  Viz = 'VIZ',
  VizChart = 'VIZ_CHART',
  VizDashboard = 'VIZ_DASHBOARD',
  Share = 'SHARE',
  Download = 'DOWNLOAD',
  Schedule = 'SCHEDULE',
  Role = 'ROLE',
  User = 'USER',
  Manager = 'MANAGER',
}

export enum VizResourceSubTypes {
  Folder = 'FOLDER',
  Storyboard = 'STORYBOARD',
}

export enum SubjectTypes {
  User = 'USER',
  Role = 'ROLE',
  UserRole = 'USER_ROLE',
  Dept = 'DEPT',
}

export enum PermissionLevels {
  Disable = 0,
  Enable = 1,
  Read = 1 << 1,
  Manage = (1 << 2) | Read,
  Grant = (1 << 3) | Read,
  Download = (1 << 5) | Read,
  Share = (1 << 6) | Read,
  Create = (1 << 7) | Manage,
}

export const RESOURCE_TYPE_PERMISSION_MAPPING = {
	[ResourceTypes.System]: [PermissionLevels.Read, PermissionLevels.Create],
	[ResourceTypes.DataAccess]: [PermissionLevels.Read, PermissionLevels.Create],
	[ResourceTypes.DataImport]: [PermissionLevels.Read, PermissionLevels.Create],
	[ResourceTypes.ExcelView]: [PermissionLevels.Read, PermissionLevels.Create],
	[ResourceTypes.File]: [PermissionLevels.Read, PermissionLevels.Create],
	[ResourceTypes.ExcelTemplate]: [PermissionLevels.Read, PermissionLevels.Create],
	[ResourceTypes.Report]: [PermissionLevels.Read, PermissionLevels.Create],
  [ResourceTypes.Viz]: [
    PermissionLevels.Read,
    PermissionLevels.Download,
    PermissionLevels.Share,
    PermissionLevels.Create,
  ],
  [ResourceTypes.View]: [PermissionLevels.Read, PermissionLevels.Create],
  [ResourceTypes.Source]: [PermissionLevels.Read, PermissionLevels.Create],
  [ResourceTypes.Schedule]: [PermissionLevels.Create],
};
