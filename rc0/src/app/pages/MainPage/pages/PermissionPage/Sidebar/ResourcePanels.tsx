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

import { Col, Radio, Row } from 'antd';
import useI18NPrefix from 'app/hooks/useI18NPrefix';
import classNames from 'classnames';
import { memo, useCallback, useMemo, useState } from 'react';
import { useSelector } from 'react-redux';
import styled from 'styled-components/macro';
import { SPACE_MD, SPACE_XS } from 'styles/StyleConstants';
import { ResourceTypes } from '../constants';
import {
  selectFolderListLoading,
  selectDatachartFolders,
  selectDashboardFolders,
  selectScheduleListLoading,
  selectSchedules,
  selectSourceListLoading,
  selectSources,
  selectStoryboardListLoading,
  selectStoryboards,
  selectViewListLoading,
  selectViews,

  selectFilemains,
	selectFilemainListLoading,
	selectFiles,
	selectFileListLoading,
	selectReports,
	selectReportListLoading,

	selectImmassets,
	selectImmassetListLoading,

	selectMiddleTables,
	selectMiddleTableListLoading,
} from '../slice/selectors';
import { FlexCollapse } from './FlexCollapse';
import { ResourceTree } from './ResourceTree';
import { PanelsProps } from './types';

const { Panel } = FlexCollapse;

export const ResourcePanels = memo(
  ({ viewpointType, onToggle, onToDetail }: PanelsProps) => {
    const [vizType, setVizType] = useState<'folder' | 'presentation'>('folder');
    const datachartFolders = useSelector(selectDatachartFolders);
    const dashboardFolders = useSelector(selectDashboardFolders);
    const storyboards = useSelector(selectStoryboards);
    const views = useSelector(selectViews);
    const sources = useSelector(selectSources);
    const schedules = useSelector(selectSchedules);
    const folderListLoading = useSelector(selectFolderListLoading);
    const storyboardListLoading = useSelector(selectStoryboardListLoading);
    const viewListLoading = useSelector(selectViewListLoading);
    const sourceListLoading = useSelector(selectSourceListLoading);
    const scheduleListLoading = useSelector(selectScheduleListLoading);
    const immassetListLoading = useSelector(selectImmassetListLoading);
    const middleTableListLoading = useSelector(selectMiddleTableListLoading);


    const filemains = useSelector(selectFilemains);
    const files = useSelector(selectFiles);
    const reports = useSelector(selectReports);
    const immassets = useSelector(selectImmassets);
    const middleTables = useSelector(selectMiddleTables);

    const filemainListLoading = useSelector(selectFilemainListLoading);
    const fileListLoading = useSelector(selectFileListLoading);
    const reportListLoading = useSelector(selectReportListLoading);



    const t = useI18NPrefix('permission');
    const [activeKeys,setActiveKeys] = useState([]);
    const resourcePanels = useMemo(
      () => [
      	{
          type: ResourceTypes.ExcelTemplate,
          dataSource: filemains,
          loading: filemainListLoading,
        },
        {
          type: ResourceTypes.Report,
          dataSource: reports,
          loading: reportListLoading,
        },
        {
          type: ResourceTypes.File,
          dataSource: files,
          loading: fileListLoading,
        },
        {
          type: ResourceTypes.VizDatachart,
          dataSource: datachartFolders,
          loading: folderListLoading,
        },
         {
          type: ResourceTypes.VizDashboard,
          dataSource: dashboardFolders,
          loading: folderListLoading,
        },
        {
          type: ResourceTypes.View,
          dataSource: views,
          loading: viewListLoading,
        },

        {
          type: ResourceTypes.ImmAsset,
          dataSource: immassets,
          loading: immassetListLoading,
        },
        {
          type: ResourceTypes.ImmData,
          dataSource: middleTables,
          loading: middleTableListLoading,
        },
        {
          type: ResourceTypes.Source,
          dataSource: sources,
          loading: sourceListLoading,
        },
        
        {
          type: ResourceTypes.Schedule,
          dataSource: schedules,
          loading: scheduleListLoading,
        },
      ],
      [
        views,
        sources,
        schedules,
        files,
        filemains,
        reports,
        datachartFolders,
        dashboardFolders,
        immassets,
        middleTables,
        viewListLoading,
        sourceListLoading,
        scheduleListLoading,
        fileListLoading,
        folderListLoading,
        reportListLoading,
        filemainListLoading,
        middleTableListLoading,
        immassetListLoading
      ],
    );

    const vizTypeChange = useCallback(e => {
      setVizType(e.target.value);
    }, []);

    return (
      <FlexCollapse activeKeys={activeKeys}  defaultActiveKeys={viewpointType && [viewpointType]}>
        {resourcePanels.map(({ type: resourceType, dataSource, loading }) => (
          <Panel
            key={resourceType}
            id={resourceType}
            title={t(`module.${resourceType.toLowerCase()}`)}
             onChange={(active,id)=>{
	            	onToggle(active,id);
	            	if(active){
	            		setActiveKeys([id])
	            	}
	            }}
          >
            
            <ResourceTree
              loading={loading}
              dataSource={dataSource}
              onSelect={onToDetail}
            />
           
          </Panel>
        ))}
      </FlexCollapse>
    );
  },
);

const VizTypeSwitch = styled(Row)`
  padding: ${SPACE_XS} ${SPACE_MD};
`;

const VizTreeWrapper = styled.div`
  display: none;

  &.selected {
    display: block;
  }
`;
