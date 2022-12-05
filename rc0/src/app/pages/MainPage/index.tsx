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

import ChartEditor, { ChartEditorBaseProps } from 'app/components/ChartEditor';
import useMount from 'app/hooks/useMount';
import ChartManager from 'app/models/ChartManager';
import { useAppSlice } from 'app/slice';
import React, { useCallback, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import {
  Redirect,
  Route,
  Switch,
  useHistory,
  useRouteMatch,
} from 'react-router';
import styled from 'styled-components/macro';
import { NotFoundPage } from '../NotFoundPage';
import { StoryEditor } from '../StoryBoardPage/Editor';
import { StoryPlayer } from '../StoryBoardPage/Player';
import { AccessRoute } from './AccessRoute';
import { Background } from './Background';
import { Navbar } from './Navbar';
import { ConfirmInvitePage } from './pages/ConfirmInvitePage';
import { MemberPage } from './pages/MemberPage';
import { OrgSettingPage } from './pages/OrgSettingPage';
import { PermissionPage } from './pages/PermissionPage';
import { ResourceTypes } from './pages/PermissionPage/constants';
import { ResourceMigrationPage } from './pages/ResourceMigrationPage';
import { SchedulePage } from './pages/SchedulePage';
import { SourcePage } from './pages/SourcePage';
import { VariablePage } from './pages/VariablePage';
import { ExcelManager } from './pages/ExcelTemplatePage/ExcelManager';
import { CategoryManager } from './pages/ExcelTemplatePage/CategoryManager';

import { MiddleTable } from './pages/MiddleTablePage';
import { Immassets } from './pages/ImmassetPage';

import { DepartmentManager } from './pages/departmentPage/DepartmentManager';
import { ReportSheets } from './pages/ExcelTemplatePage/ReportSheets';
import { Reports } from './pages/ReportPage';
import { ReportEditor } from './pages/ReportPage/ReportEditor';


import { Files } from './pages/FilePage';

import { Task } from './pages/TaskPage';


import { ViewPage } from './pages/ViewPage';
import { useViewSlice } from './pages/ViewPage/slice';
import { VizPage } from './pages/VizPage';
import { useVizSlice } from './pages/VizPage/slice';
import { initChartPreviewData } from './pages/VizPage/slice/thunks';
import { useMainSlice } from './slice';
import { selectOrgId } from './slice/selectors';
import {
  getDataProviders,
  getLoggedInUserPermissions,
  getUserSettings,
} from './slice/thunks';
import { getToken } from 'utils/auth';

import {
 
  SPACE_TIMES,

} from 'styles/StyleConstants';
import { MainPageRouteParams } from './types';

export function MainPage() {
  useAppSlice();
  const { actions } = useMainSlice();
  const { actions: vizActions } = useVizSlice();
  const { actions: viewActions } = useViewSlice();
  const dispatch = useDispatch();
  const organizationMatch = useRouteMatch<MainPageRouteParams>(
    '/organizations/:orgId',
  );
  const reportEditorMatch = useRouteMatch<{type:string}>(
    '/organizations/:orgId/:type/editor',
  );
  const taskTypeMatch = useRouteMatch<{type:string}>(
    '/organizations/:orgId/tasks/:type/',
  );
  const vizModuleMatch = useRouteMatch<{type:string}>(
    '/organizations/:orgId/:moduleName',
  );
  const orgId = useSelector(selectOrgId);
  const history = useHistory();
  // loaded first time

  useMount(
    () => {
      ChartManager.instance()
        .load()
        .catch(err =>
          console.error('Fail to load customize charts with ', err),
        );
      dispatch(getUserSettings(organizationMatch?.params.orgId));
      dispatch(getDataProviders());
    },
    () => {
      dispatch(actions.clear());
    },
  );

  useEffect(() => {
    if (orgId) {
      dispatch(vizActions.clear());
      dispatch(viewActions.clear());
      dispatch(getLoggedInUserPermissions(orgId));
    }
  }, [dispatch, vizActions, viewActions, orgId]);

  const onSaveInDataChart = useCallback(
    (orgId: string, backendChartId: string) => {
      dispatch(
        initChartPreviewData({
          backendChartId,
          orgId,
        }),
      );
      
      //history.push(`/organizations/${orgId}/${vizModuleMatch.params.moduleName}/${backendChartId}`);
      history.push(`/organizations/${orgId}/vizdatacharts/${backendChartId}`);
    },
    [dispatch, history,vizModuleMatch],
  );


  
  
  return (
    <AppContainer>
      <Background />
      <Navbar />
      {orgId && (
        <Switch>
          <Route path="/" exact>
            <Redirect to={`/organizations/${orgId}`} />
          </Route>
          <Route path="/confirminvite" component={ConfirmInvitePage} />
          <Route path="/organizations/:orgId" exact>
            <Redirect
              to={`/organizations/${organizationMatch?.params.orgId}/tasks/involveds`}
            />
          </Route>

          <Route path="/organizations/:orgId/immscreen" exact>
            <Redirect
              to={`/upload/web/index.html`}
            />
          </Route>



          <Route
            path="/organizations/:orgId/vizs/chartEditor"
            render={res => {
              const hisSearch = new URLSearchParams(res.location.search);
              const hisState = {
                dataChartId: hisSearch.get('dataChartId') || '',
                chartType: hisSearch.get('chartType') || 'dataChart',
                container: hisSearch.get('container') || 'dataChart',
                defaultViewId: hisSearch.get('defaultViewId') || '',
              } as ChartEditorBaseProps;
              return (
                <AccessRoute module={ResourceTypes.Viz}>
                  <ChartEditor
                    dataChartId={hisState.dataChartId}
                    orgId={orgId}
                    chartType={hisState.chartType}
                    container={hisState.container}
                    defaultViewId={hisState.defaultViewId}
                    onClose={() => history.go(-1)}
                    onSaveInDataChart={onSaveInDataChart}
                  />
                </AccessRoute>
              );
            }}
          />


          <Route
            path="/organizations/:orgId/:type/editor"
            render={res => {

            	const type = reportEditorMatch.params?.type ? String(reportEditorMatch.params.type).replace(/Report$/,'') : 'data';
       				
              const hisSearch = new URLSearchParams(res.location.search);
              const editorProps = {
                id: hisSearch.get('id') || '',
                type:type+'info',
                orgId:orgId,
                path:reportEditorMatch.params.type,
                title:hisSearch.get('name'),
                token: getToken(false),
                module:hisSearch.get('module'),
              };

              return (
                <AccessRoute module={ResourceTypes.Report}>
                  <ReportEditor {...editorProps}  orgId={orgId} />
                </AccessRoute>
              );
            }}
          />

          

          {/*<Route
            path="/organizations/:orgId/vizs/storyPlayer/:storyId"
            render={() => <StoryPlayer />}
          />
          <Route
            path="/organizations/:orgId/vizs/storyEditor/:storyId"
            render={() => <StoryEditor />}
          />*/}
          {/*<Route
            path="/organizations/:orgId/vizs/:vizId?"
            render={() => (
              <AccessRoute module={ResourceTypes.Viz}>
                <VizPage />
              </AccessRoute>
            )}
          />
          */}
          <Route path="/organizations/:orgId/vizdatacharts/:vizId?" render={()=>(<AccessRoute module={ResourceTypes.VizDatachart}>
              <VizPage vizDatatype="DATACHART" />
            </AccessRoute>)} />
            
 					<Route path="/organizations/:orgId/vizdashboards/:vizId?" render={()=>(<AccessRoute module={ResourceTypes.VizDashboard}>
              <VizPage vizDatatype="DASHBOARD" />
            </AccessRoute>)} />
          
          
 		
          <Route path="/organizations/:orgId/tasks" exact>
            <Redirect
              to={`/organizations/${organizationMatch?.params.orgId}/tasks/involveds`}
            />
          </Route>
          <Route
            path="/organizations/:orgId/tasks/:type"
            render={() => (
              <AccessRoute module={ResourceTypes.Task}>
                <Task taskType={taskTypeMatch?.params?.type ?? 'involveds'}  />
              </AccessRoute>
            )}
          />
          <Route
            path="/organizations/:orgId/views/:viewId?"
            render={() => (
              <AccessRoute module={ResourceTypes.View}>
                <ViewPage />
              </AccessRoute>
            )}
          />
          <Route
            path="/organizations/:orgId/sources"
            render={() => (
              <AccessRoute module={ResourceTypes.Source}>
                <SourcePage />
              </AccessRoute>
            )}
          />
          <Route
            path="/organizations/:orgId/schedules"
            render={() => (
              <AccessRoute module={ResourceTypes.Schedule}>
                <SchedulePage />
              </AccessRoute>
            )}
          />
          <Route
            path="/organizations/:orgId/members"
            render={() => (
              <AccessRoute module={ResourceTypes.User}>
                <MemberPage />
              </AccessRoute>
            )}
          />
          <Route
            path="/organizations/:orgId/roles"
            render={() => (
              <AccessRoute module={ResourceTypes.User}>
                <MemberPage />
              </AccessRoute>
            )}
          />
          <Route path="/organizations/:orgId/permissions" exact>
            <Redirect
              to={`/organizations/${organizationMatch?.params.orgId}/permissions/subject`}
            />
          </Route>
          <Route
            path="/organizations/:orgId/permissions/:viewpoint"
            render={() => (
              <AccessRoute module={ResourceTypes.Permission}>
                <PermissionPage />
              </AccessRoute>
            )}
          />
          <Route
            path="/organizations/:orgId/variables"
            render={() => (
              <AccessRoute module={ResourceTypes.Variable}>
                <VariablePage />
              </AccessRoute>
            )}
          />

           <Route
            path="/organizations/:orgId/exceltemplates"
            render={() => (
              <AccessRoute module={ResourceTypes.ExcelTemplate}>
                <ExcelManager />
              </AccessRoute>
            )}
          />

          <Route
            path="/organizations/:orgId/datasays"
            render={() => (
              <div style={{height:400,textAlign:'center',fontSize:'50px',lineHeight:'400px',width:'100%'}}>正在建设中...</div>
            )}
          />

          <Route
            path="/organizations/:orgId/files"
            render={() => (
              <AccessRoute module={ResourceTypes.File}>
                <Files />
              </AccessRoute>
            )}
          />
          <Route
            path="/organizations/:orgId/excelviews"
            render={() => (
              <AccessRoute module={ResourceTypes.ExcelView}>
                <ReportSheets />
              </AccessRoute>
            )}
          />
          <Route
            path="/organizations/:orgId/categories"render={() => (
              <AccessRoute module={ResourceTypes.ExcelTemplate}>
                <CategoryManager />
              </AccessRoute>
            )}
          />

          <Route
            path="/organizations/:orgId/departments"
            render={() => (
              <AccessRoute module={ResourceTypes.Department}>
                <DepartmentManager />
              </AccessRoute>
            )}
          />


          <Route
            path="/organizations/:orgId/immassets"
            render={() => (
              <AccessRoute module={ResourceTypes.ImmAsset}>
                <Immassets />
              </AccessRoute>
            )}
          />
          <Route
            path="/organizations/:orgId/immdatas"
            render={() => (
              <AccessRoute module={ResourceTypes.ImmData}>
                <MiddleTable />
              </AccessRoute>
            )}
          />




          <Route
            path="/organizations/:orgId/reports"
            render={() => (
              <AccessRoute module={ResourceTypes.Report}>
                <Reports type="datainfo" />
              </AccessRoute>
            )}
          />
          
          <Route
            path="/organizations/:orgId/orgSettings"
            render={() => (
              <AccessRoute module={ResourceTypes.Manager}>
                <OrgSettingPage />
              </AccessRoute>
            )}
          />
          <Route
            path="/organizations/:orgId/resourceMigration"
            render={() => {
            	const hisSearch = new URLSearchParams(res.location.search);
              return (<AccessRoute module={ResourceTypes.ResourceMigration}>
                <ResourceMigrationPage vizDatatype={hisSearch.get('vizDatatype') } />
              </AccessRoute>
            )}}
          />
          <Route path="*" component={NotFoundPage} />
        </Switch>
      )}
    </AppContainer>
  );
}

const AppContainer = styled.main`
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  display: flex;
  padding-top:48px;
  background-color: ${p => p.theme.bodyBackground};

  .container-wrap{
  	padding-top:10px;
  }
`;
