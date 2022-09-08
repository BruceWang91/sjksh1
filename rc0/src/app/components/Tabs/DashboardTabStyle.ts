import { createGlobalStyle } from 'styled-components/macro';
import {
  FONT_WEIGHT_MEDIUM,
  SPACE,
  SPACE_TIMES,
  SPACE_MD,
  SPACE_SM,
  SPACE_XS,
  SPACE_LG,
} from 'styles/StyleConstants';

export const DashboardTabStyle = createGlobalStyle`
  .ant-tabs{
    padding: 0 ${SPACE_LG};
    user-select: none;
    background-color: ${p => p.theme.componentBackground};
    border-bottom: 1px solid ${p => p.theme.borderColorSplit};
    .ant-tabs-nav-wrap{
    	margin: 0 -${SPACE} !important
    }
    &.ant-tabs-top > .ant-tabs-nav,
    &.ant-tabs-bottom > .ant-tabs-nav,
    &.ant-tabs-top > div > .ant-tabs-nav,
    &.ant-tabs-bottom > div > .ant-tabs-nav {
      margin: 0;
    }

    &.ant-tabs-top > .ant-tabs-nav::before,
    &.ant-tabs-bottom > .ant-tabs-nav::before,
    &.ant-tabs-top > div > .ant-tabs-nav::before,
    &.ant-tabs-bottom > div > .ant-tabs-nav::before {
      border-bottom: 0;
    }

    .ant-tabs-tab-remove{
    	margin:0;
    	position:absolute;
    	right:-2px;
    	opacity:0;
    }
    &.ant-tabs-card {
      &.ant-tabs-card > .ant-tabs-nav .ant-tabs-tab,
      &.ant-tabs-card > div > .ant-tabs-nav .ant-tabs-tab {
        padding: ${SPACE} ${SPACE_SM};
        margin: ${SPACE} ${SPACE};
        font-weight: ${FONT_WEIGHT_MEDIUM};
        color: ${p => p.theme.textColorSnd};
        background-color: ${p => p.theme.componentBackground};
        border: none;
        border-radius: 0;

        &:hover {
          background-color: ${p => p.theme.bodyBackground};
          padding: ${SPACE} ${SPACE_LG} ${SPACE} ${SPACE_SM};
          .ant-tabs-tab-remove {
            opacity:1;
          }
        }
      }

      &.ant-tabs-card > .ant-tabs-nav .ant-tabs-tab-active,
      &.ant-tabs-card > div > .ant-tabs-nav .ant-tabs-tab-active {
        background-color: ${p => p.theme.bodyBackground};
      }
    }
  }
`;
