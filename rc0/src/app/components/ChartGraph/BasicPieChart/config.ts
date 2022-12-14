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

import { ChartConfig } from 'app/types/ChartConfig';

const config: ChartConfig = {
  datas: [
    {
      label: 'dimension',
      key: 'dimension',
      required: true,
      type: 'group',
      limit: [0, 999],
      actions: {
        NUMERIC: ['alias', 'colorize', 'sortable'],
        STRING: ['alias', 'colorize', 'sortable'],
        DATE: ['alias', 'colorize', 'sortable'],
      },
      drillable: true,
    },
    {
      label: 'metrics',
      key: 'metrics',
      required: true,
      type: 'aggregate',
      limit: [1, 999],
    },
    {
      label: 'filter',
      key: 'filter',
      type: 'filter',
      allowSameField: true,
    },
    {
      label: 'info',
      key: 'info',
      type: 'info',
    },
  ],
  styles: [
    {
      label: 'label.title',
      key: 'label',
      comType: 'group',
      rows: [
        {
          label: 'label.showLabel',
          key: 'showLabel',
          default: true,
          comType: 'checkbox',
        },
        {
          label: 'label.position',
          key: 'position',
          comType: 'select',
          default: 'outside',
          options: {
            translateItemLabel: true,
            items: [
              {
                label: '@global@.label.positionType.outside',
                value: 'outside',
              },
              { label: '@global@.label.positionType.inside', value: 'inside' },
              { label: '@global@.label.positionType.center', value: 'center' },
            ],
          },
        },
        {
          label: 'viz.palette.style.font',
          key: 'font',
          comType: 'font',
          default: {
            fontFamily: 'PingFang SC',
            fontSize: '12',
            fontWeight: 'normal',
            fontStyle: 'normal',
            color: '#495057',
          },
        },
        {
          label: 'label.showName',
          key: 'showName',
          default: true,
          comType: 'checkbox',
        },
        {
          label: 'label.showValue',
          key: 'showValue',
          default: false,
          comType: 'checkbox',
        },
        {
          label: 'label.showPercent',
          key: 'showPercent',
          default: true,
          comType: 'checkbox',
        },
      ],
    },
    {
      label: 'legend.title',
      key: 'legend',
      comType: 'group',
      rows: [
        {
          label: 'legend.showLegend',
          key: 'showLegend',
          default: true,
          comType: 'checkbox',
        },
        {
          label: 'legend.type',
          key: 'type',
          comType: 'legendType',
          default: 'scroll',
        },
        {
          label: 'legend.selectAll',
          key: 'selectAll',
          default: true,
          comType: 'checkbox',
        },
        {
          label: 'legend.position',
          key: 'position',
          comType: 'legendPosition',
          default: 'right',
        },
        {
          label: 'legend.height',
          key: 'height',
          default: 0,
          comType: 'inputNumber',
          options: {
            step: 40,
            min: 0,
          },
        },
        {
          label: 'viz.palette.style.font',
          key: 'font',
          comType: 'font',
          default: {
            fontFamily: 'PingFang SC',
            fontSize: '12',
            fontWeight: 'normal',
            fontStyle: 'normal',
            color: '#495057',
          },
        },
      ],
    },
    {
      label: 'viz.palette.style.margin.title',
      key: 'margin',
      comType: 'group',
      rows: [
        {
          label: 'viz.palette.style.margin.containLabel',
          key: 'containLabel',
          default: true,
          comType: 'checkbox',
        },
        {
          label: 'viz.palette.style.margin.left',
          key: 'marginLeft',
          default: '5%',
          comType: 'marginWidth',
        },
        {
          label: 'viz.palette.style.margin.right',
          key: 'marginRight',
          default: '5%',
          comType: 'marginWidth',
        },
        {
          label: 'viz.palette.style.margin.top',
          key: 'marginTop',
          default: '5%',
          comType: 'marginWidth',
        },
        {
          label: 'viz.palette.style.margin.bottom',
          key: 'marginBottom',
          default: '5%',
          comType: 'marginWidth',
        },
      ],
    },
  ],
  settings: [
    {
      label: 'viz.palette.setting.paging.title',
      key: 'paging',
      comType: 'group',
      rows: [
        {
          label: 'viz.palette.setting.paging.pageSize',
          key: 'pageSize',
          default: 1000,
          comType: 'inputNumber',
          options: {
            needRefresh: true,
            step: 1,
            min: 0,
          },
        },
      ],
    },
  ],
  interactions: [
    {
      label: 'drillThrough.title',
      key: 'drillThrough',
      comType: 'checkboxModal',
      default: false,
      options: { modalSize: 'middle' },
      rows: [
        {
          label: 'drillThrough.title',
          key: 'setting',
          comType: 'interaction.drillThrough',
        },
      ],
    },
    {
      label: 'viewDetail.title',
      key: 'viewDetail',
      comType: 'checkboxModal',
      default: false,
      options: { modalSize: 'middle' },
      rows: [
        {
          label: 'viewDetail.title',
          key: 'setting',
          comType: 'interaction.viewDetail',
        },
      ],
    },
  ],
  i18ns: [
    {
      lang: 'zh-CN',
      translation: {
        section: {
          legend: '??????',
          detail: '????????????',
        },
        common: {
          showLabel: '????????????',
          rotate: '????????????',
          position: '??????',
        },
        pie: {
          title: '??????',
          circle: '??????',
          roseType: '??????????????????',
        },
        label: {
          title: '??????',
          showLabel: '????????????',
          position: '??????',
          positionType: {
            outside: '??????',
            inside: '??????',
            center: '??????',
          },
          showName: '?????????',
          showPercent: '?????????',
          showValue: '?????????',
        },
        legend: {
          title: '??????',
          showLegend: '????????????',
          type: '????????????',
          selectAll: '????????????',
          position: '????????????',
          height: '????????????',
        },
        reference: {
          title: '?????????',
          open: '?????????????????????',
        },
        tooltip: {
          title: '????????????',
          showPercentage: '?????????????????????',
        },
      },
    },
    {
      lang: 'en-US',
      translation: {
        section: {
          legend: 'Legend',
          detail: 'Detail',
        },
        common: {
          showLabel: 'Show Label',
          rotate: 'Rotate',
          position: 'Position',
          height: 'Height',
        },
        pie: {
          title: 'Pie',
          circle: 'Circle',
          roseType: 'Rose',
        },
        label: {
          title: 'Label',
          showLabel: 'Show Label',
          position: 'Position',
          positionType: {
            outside: 'Outside',
            inside: 'Inside',
            center: 'Center',
          },
          showName: 'Show Name',
          showPercent: 'Show Percentage',
          showValue: 'Show Value',
        },
        legend: {
          title: 'Legend',
          showLegend: 'Show Legend',
          type: 'Type',
          selectAll: 'Select All',
          position: 'Position',
          height: 'Height',
        },
        reference: {
          title: 'Reference',
          open: 'Open',
        },
        tooltip: {
          title: 'Tooltip',
          showPercentage: 'Show Percentage',
        },
      },
    },
  ],
};

export default config;
