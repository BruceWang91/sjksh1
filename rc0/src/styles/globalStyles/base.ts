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

import { createGlobalStyle } from 'styled-components/macro';
import { FONT_FAMILY, FONT_SIZE_BODY } from 'styles/StyleConstants';
import 'react-base-table/styles.css';
/* istanbul ignore next */
export const Base = createGlobalStyle`
  body {
    font-family: ${FONT_FAMILY};
    font-size: ${FONT_SIZE_BODY};
    background-color: ${p => p.theme.bodyBackground};

    h1,h2,h3,h4,h5,h6 {
      margin: 0;
      font-weight: inherit;
      color: inherit;
    }

    p, figure {
      margin: 0;
    }

    ul {
      padding: 0;
      margin: 0;
    }

    li {
      list-style-type: none;
    }

    input {
      padding: 0;
    }

    table th {
      padding: 0;
      text-align: center;
    }

    * {
      -webkit-overflow-scrolling: touch;
    }
    a:hover{
    	color:#D5AA44
    }
    .ant-menu-submenu-popup > ul{
    	margin-top:-5px;
    	border-radius:0;
    }
    .ant-collapse{
    border:0;
    background-color: transparent;
    }

    .ant-collapse > .ant-collapse-item{
    border-top:1px solid ${p => p.theme.borderColorSplit};
    border-bottom:0;

    }
    .ant-collapse-content{
    border-top:0
    }
    .ant-form-vertical .ant-form-item .ant-form-item-control{
    	width:100%;
    }
    .ant-form-item{
    	margin: 0 0 16px;
    }
    .ant-form-item-label>label{
    	font-size:12px;
    	padding:0 4px 0 0;
    }
    .ant-tree .ant-tree-node-content-wrapper.ant-tree-node-selected {
		    background-color: transparent;
		    color:${p => p.theme.primary};
		    font-weight:bold;
		}
		.ant-select-tree-iconEle{
			color: ${p => p.theme.textColorLight};
		}

  }
  .ant-transfer-list-body-customize-wrapper{
  	overflow-y:auto;
  }
  
  .scroll{
  	width:100%;
  	height:100%;
  	overflow:auto;
  }

  div::-webkit-scrollbar,
	.div-scroll::-webkit-scrollbar {
	  width: 7px;     
	  height: 7px;
	}
	div::-webkit-scrollbar-thumb,
	.div-scroll::-webkit-scrollbar-thumb {
	  border-radius: 10px;
	   -webkit-box-shadow: inset 0 0 5px rgba(0,0,0,0.2);
	  background: ${p => p.theme.emphasisPrimary};
	  cursor:pointer;
	}
	div::webkit-scrollbar-track,
	.div-scroll::-webkit-scrollbar-track {
	  -webkit-box-shadow: inset 0 0 5px rgba(0,0,0,0.2);
	  border-radius: 10px;
	  background: rgba(0,35,88,0.1);
	}
  .zj-layout{
    position:relative;
    box-sizing:border-content;
    width:100%;
    height:100%;
    & > &-handle-mask{
        background:rgba(0,0,0,0.1);
        width:100%;
        height:100%;
        position:absolute;
        top:0;
        bottom:0;
        z-index:2;
    }
    &&-horizontal{
        display:flex;
    }
    &-gap-handle{
        background-color:${p => p.theme.componentBackground};
        position:absolute;
        top:0;
        left:0;
        width:0;
        height:0;
        overflow:hidden;
        z-index:1;
    }
    &-item{
        position:relative;
        &-tsx{
            will-change:width;
            transition:width  0.2s cubic-bezier(0.215, 0.61, 0.355, 1);
        }
        &-tsy{
            will-change:height;
            transition:height 0.2s cubic-bezier(0.215, 0.61, 0.355, 1);
        }
        &-container{
            background-color:transparent;
            width:100%;
            height:100%;
            overflow:hidden;
        }
        &-gap{
            background-color:transparent;
            position:absolute;
            z-index:1;
        }
        &-gap[disabled]{
            pointer-events:none;
        }
        &-gap:hover{
            background-color:${p => p.theme.emphasisBackground};
        }
        &-arrow{
            position:absolute;
            width:10px;
            height:10px;
            z-index:1;
            cursor:pointer;
            background-color:${p => p.theme.primary};
            & > .slide-arrow{
                position:absolute;
                width:3px;
                height:6px;

            }
            & > .slide-arrow:after{
                position:absolute;
                content:'';
                display:block;
                vertical-align:0.1em;
                width: 0; 
                height: 0;
                border-width: 3px;
                border-style: solid;
                border-color:transparent;
                
            }
        }
    }
    &&-horizontal &-item{
    flex-shrink:0;
    }
    &&-horizontal  &-item:last-child{
    padding-right:0 !important;
    }
    &&-horizontal  &-item:last-child > &-item-arrow,
    &&-horizontal  &-item:last-child > &-item-gap{
    display:none;
    }
    &&-horizontal > &-item > &-item-gap{
    height:100%;
    top:0;
    right:0;
    cursor:col-resize
    }
    &&-horizontal > &-item > &-item-arrow{
        right:0;
        top:0;
        bottom:0;
        margin:auto 0;
        width:5px;
        height:30px;
        border-radius:4px;
        & > .slide-arrow{
            top:0;
            bottom:0;
            margin:auto;
            left:0;
            right:0;
        }
        & > .closed-slide-arrow:after{
           margin-left:-3px;
           border-right-color:${p => p.theme.componentBackground};
        }
        & > .opened-slide-arrow:after{
           border-left-color:${p => p.theme.componentBackground};
        }
    }
    &&-horizontal > &-item > &-item-arrow:hover{
        background-color:${p => p.theme.emphasisPrimary};
        & > .closed-slide-arrow:after{
           border-right-color:${p => p.theme.componentBackground};
        }
        & > .opened-slide-arrow:after{
           border-left-color:${p => p.theme.componentBackground};
        }
    }
    &&-vertical  &-item:last-child{
    padding-bottom:0 !important;
    }
    &&-vertical  &-item:last-child > &-item-arrow,
    &&-vertical  &-item:last-child > &-item-gap{
    display:none;
    }
    &&-vertical > &-item > &-item-gap{
    width:100%;
    bottom:0;
    left:0;
    cursor:row-resize
    }
    &&-vertical > &-item > &-item-arrow{
        right:0;
        left:0;
        bottom:0;
        margin:0 auto;
        width:30px;
        height:5px;
        border-radius:4px;
        & > .slide-arrow{
            top:0;
            bottom:0;
            margin:auto;
            left:0;
            right:0;
            height:3px;
            width:6px;
        }
        & > .closed-slide-arrow:after{
           margin-top:-3px;
           border-bottom-color:${p => p.theme.componentBackground};
        }
        & > .opened-slide-arrow:after{
           border-top-color:${p => p.theme.componentBackground};
        }
    }
    &&-vertical > &-item > &-item-arrow:hover{
        background-color:${p => p.theme.emphasisPrimary};
        & > .closed-slide-arrow:after{
           border-bottom-color:${p => p.theme.componentBackground};
        }
        & > .opened-slide-arrow:after{
           border-top-color:${p => p.theme.componentBackground};
        }
    }
}
`;
