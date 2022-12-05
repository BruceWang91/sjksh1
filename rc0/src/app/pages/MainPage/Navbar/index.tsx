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

import {
  BankFilled,
  TableOutlined,
  ExportOutlined,
  FormOutlined,
  ProfileOutlined,
  UserOutlined
} from '@ant-design/icons';
import { List, Menu, Tooltip } from 'antd';
import logo from 'app/assets/images/logo.svg';
import LogoText from 'app/components/Brand/LogoText';
import { Avatar, MenuListItem, Popup } from 'app/components';
import { TenantManagementMode } from 'app/constants';
import useI18NPrefix from 'app/hooks/useI18NPrefix';
import {
  selectCurrentOrganization,
  selectDownloadPolling,
  selectOrganizationListLoading,
  selectOrgId,
} from 'app/pages/MainPage/slice/selectors';
import { getOrganizations } from 'app/pages/MainPage/slice/thunks';
import { selectLoggedInUser, selectSystemInfo } from 'app/slice/selectors';
import { logout } from 'app/slice/thunks';
import { downloadFile } from 'app/utils/fetch';
import { BASE_RESOURCE_URL } from 'globalConstants';
import { changeLang } from 'locales/i18n';
import { cloneElement, useCallback, useMemo, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useDispatch, useSelector } from 'react-redux';
import { NavLink, useHistory, useRouteMatch } from 'react-router-dom';
import styled from 'styled-components/macro';
import {
  BLACK,
  BORDER_RADIUS,
  FONT_SIZE_ICON_SM,
  FONT_SIZE_ICON_XS,
  FONT_WEIGHT_MEDIUM,
  FONT_SIZE_TITLE,
  LEVEL_10,
  SPACE_LG,
  SPACE_MD,
  SPACE_TIMES,
  SPACE_XS,
  WHITE,
} from 'styles/StyleConstants';
import themeSlice from 'styles/theme/slice';
import { selectThemeKey } from 'styles/theme/slice/selectors';
import { ThemeKeyType } from 'styles/theme/slice/types';
import { changeAntdTheme, saveTheme } from 'styles/theme/utils';
import { Access ,MenuAccess} from '../Access';
import {
  PermissionLevels,
  ResourceTypes,
} from '../pages/PermissionPage/constants';
import { useMainSlice } from '../slice';
import { DownloadListPopup } from './DownloadListPopup';
import { ModifyPassword } from './ModifyPassword';
import { OrganizationList } from './OrganizationList';
import { Profile } from './Profile';
import { loadTasks } from './service';
import classnames from  'classnames';

import {MENUS} from './Menus';




export function Navbar() {
  const { actions } = useMainSlice();
  const [profileVisible, setProfileVisible] = useState(false);
  const [modifyPasswordVisible, setModifyPasswordVisible] = useState(false);
  const dispatch = useDispatch();
  const history = useHistory();
  const { i18n } = useTranslation();
  const systemInfo = useSelector(selectSystemInfo);
  const orgId = useSelector(selectOrgId);
  const currentOrganization = useSelector(selectCurrentOrganization);
  const loggedInUser = useSelector(selectLoggedInUser);
  const organizationListLoading = useSelector(selectOrganizationListLoading);
  const downloadPolling = useSelector(selectDownloadPolling);
  const themeKey = useSelector(selectThemeKey);
  const matchModules = useRouteMatch<{ moduleName: string }>(
    '/organizations/:orgId/:moduleName',
  );

  const t = useI18NPrefix('main');
  const brandClick = useCallback(() => {
    history.push('/');
  }, [history]);

  const hideProfile = useCallback(() => {
    setProfileVisible(false);
  }, []);
  const hideModifyPassword = useCallback(() => {
    setModifyPasswordVisible(false);
  }, []);

  const organizationListVisibleChange = useCallback(
    visible => {
      if (visible && !organizationListLoading) {
        dispatch(getOrganizations());
      }
    },
    [dispatch, organizationListLoading],
  );






  

  const handleChangeThemeFn = useCallback(
    (theme: ThemeKeyType) => {
      if (themeKey !== theme) {
        dispatch(themeSlice.actions.changeTheme(theme));
        changeAntdTheme(theme);
        saveTheme(theme);
      }
    },
    [dispatch, themeKey],
  );

  const userMenuSelect = useCallback(
    ({ key }) => {
      switch (key) {
        case 'profile':
          setProfileVisible(true);
          break;
        case 'logout':
          dispatch(
            logout(() => {
              history.replace('/');
            }),
          );
          break;
        case 'password':
          setModifyPasswordVisible(true);
          break;
        case 'zh':
        case 'en':
          if (i18n.language !== key) {
            changeLang(key);
          }
          break;
        case 'dark':
        case 'light':
          handleChangeThemeFn(key);
          break;
        default:
          break;
      }
    },
    [dispatch, history, i18n, handleChangeThemeFn],
  );

  const onSetPolling = useCallback(
    (polling: boolean) => {
      dispatch(actions.setDownloadPolling(polling));
    },
    [dispatch, actions],
  );



  const mainNavSelectKeys = useMemo(()=>{
  	const search = new URLSearchParams(history.location.search);
  	const moduleType = search.get('moduleType');
  	const moduleName = matchModules?.params?.moduleName;
  	if(moduleType){
  		return [`${moduleName}_${moduleType}`]
  	}
  	return [moduleName]
  },[matchModules,history]);


  return (
    <>
      <MainNav>
        <Brand onClick={brandClick}>
          <img src={logo} alt="logo"/>
          <LogoText size={48}/>

        </Brand>
     
        <Nav>
        	<MenuAccess onLeafClick={ (name,link)=>{
        		if(!link){
        			history.push(`/organizations/${orgId}/${name}`)
        		}else{
        			let a = document.createElement('a');
							a.href = link;
							a.target = '_blank';
							document.body.appendChild(a);
							a.click();
							document.body.removeChild(a);
        		}
        		
        	} } menus={MENUS}  selectedKeys={mainNavSelectKeys} />
        </Nav>
        <Toolbar>
          {/*<DownloadListPopup
            polling={downloadPolling}
            setPolling={onSetPolling}
            onLoadTasks={loadTasks}
            onDownloadFile={item => {
              if (item.id) {
                downloadFile(item.id).then(() => {
                  dispatch(actions.setDownloadPolling(true));
                });
              }
            }}
          />*/}
          {systemInfo?.tenantManagementMode ===
          TenantManagementMode.Platform && (
            <Popup
              content={<OrganizationList/>}
              trigger={['click']}
              placement="bottomRight"
              onVisibleChange={organizationListVisibleChange}
            >
              <li>
                <Tooltip title={t('nav.organization.title')} placement="right">
                  <Avatar
                    src={`${BASE_RESOURCE_URL}${currentOrganization?.avatar}`}
                  >
                    <BankFilled/>
                  </Avatar>
                </Tooltip>
              </li>
            </Popup>
          )}
          <Popup
            content={
              <Menu
                prefixCls="ant-dropdown-menu"
                selectable={false}
                onClick={userMenuSelect}
              >
                <Menu.Divider/>
                <MenuListItem
                  key="profile"
                  prefix={<ProfileOutlined className="icon"/>}
                >
                  <p>{t('nav.account.profile.title')}</p>
                </MenuListItem>
                <MenuListItem
                  key="password"
                  prefix={<FormOutlined className="icon"/>}
                >
                  <p>{t('nav.account.changePassword.title')}</p>
                </MenuListItem>
                <MenuListItem
                  key="logout"
                  prefix={<ExportOutlined className="icon"/>}
                >
                  <p>{t('nav.account.logout.title')}</p>
                </MenuListItem>
              </Menu>
            }
            trigger={['click']}
            placement="bottomRight"
          >
            <li>
              <Avatar src={`${BASE_RESOURCE_URL}${loggedInUser?.avatar}`}>
                <UserOutlined/>
              </Avatar>
            </li>
          </Popup>
        </Toolbar>
        <Profile visible={profileVisible} onCancel={hideProfile}/>
        <ModifyPassword
          visible={modifyPasswordVisible}
          onCancel={hideModifyPassword}
        />
      </MainNav>
     
    </>
  );
}

const MainNav = styled.div`
	position:fixed;
	top:0;
  z-index: ${LEVEL_10};
  display: flex;
  flex-direction: row;
  flex-shrink: 0;
  width: 100%;
  height:48px;
  background: #026968;


  
`;

const Brand = styled.div`
  display: flex;
  flex-shrink: 0;
  align-items: center;
  justify-content: start;
  height: ${SPACE_TIMES(12)};
  width:288px;
  cursor: pointer;

  background: #3b948c;
  color: #fff;
   img {
   	margin:0 6px;
    width: ${SPACE_TIMES(9)};
    height: ${SPACE_TIMES(9)};
  }



`;

const Nav = styled.nav`




  flex: 1;
  padding: 0;
  .ant-menu{
  	color:#fff
  }
  
  .header-nav{
		border-bottom:0;
  	line-height:48px;
  	background-color:transparent;

  	>.ant-menu-item:after, >.ant-menu-submenu:after{
	  	display:none;
	  }

	  > .ant-menu-submenu:not(.ant-menu-submenu-selected),
  	> .ant-menu-item:not(.ant-menu-item-selected){
  		color:#fff !important
  	}
  	> .ant-menu-submenu:not(.ant-menu-submenu-selected) {
  		color:#fff !important
  	}
  	> .ant-menu-submenu:not(.ant-menu-submenu-selected):hover,
  	> .ant-menu-item:not(.ant-menu-item-selected):hover{
  		background:#024a43 !important;
  		color:#fff !important
  	}
  	> .ant-menu-submenu-selected,
  	> .ant-menu-item-selected{
  		background:#fff !important;
  		color:${p => p.theme.primary};
  	}
  	.ant-menu-submenu-title{
  		padding: 0 20px;
  	}
  	.ant-menu-submenu:not(.ant-menu-submenu-selected) .ant-menu-submenu-title{
  		color:#fff;

    	

  	}
  	.ant-menu-submenu{
			padding:0
		}
	}
 	


  
 
`;

const NavItem = styled(NavLink)`
  display: flex;
  flex-direction: row;
  align-items: center;
  padding: 0 ${SPACE_XS} ;
  color: ${p => p.theme.textColorDisabled};
  border-radius: 0;
  transition: none;
  height: ${SPACE_TIMES(12)};
  font-size:${FONT_SIZE_TITLE};
  &:hover,
  &.active {
    color: ${p => p.theme.primary};
    background-color: ${p => p.theme.emphasisBackground};

    h2 {
      font-weight: ${FONT_WEIGHT_MEDIUM};
      color: ${p => p.theme.textColor};
    }
  }

  .anticon  {
    font-size: ${FONT_SIZE_ICON_XS};
    margin-right:${SPACE_TIMES(1)};
  }
  .iconfont{
  	font-size: ${FONT_SIZE_ICON_XS};
  	margin-right:${SPACE_TIMES(1)};
  }
  > span {
  	margin-top:-3px;
  }
`;

const Toolbar = styled.ul`
  flex-shrink: 0;
  display:flex;

  > li {
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 0 ${SPACE_XS};
    font-size: ${FONT_SIZE_ICON_SM};
    color: ${p => p.theme.textColorDisabled};
    cursor: pointer;
    border-radius: ${BORDER_RADIUS};
    height:${SPACE_TIMES(12)};
    &:hover {
      color: ${p => p.theme.primary};
      background-color: ${p => p.theme.bodyBackground};
    }
  }
`;

const SubNav = styled.div`
  display: flex;
  flex-direction: column;
  flex-shrink: 0;
  width: ${SPACE_TIMES(64)};
  padding: ${SPACE_MD} 0;
  background-color: ${p => p.theme.componentBackground};
  border-color:${p => p.theme.borderColorSplit};
  border-style: solid;
  border-width:1px 1px 1px 0;
`;

const SubNavTitle = styled(NavLink)`
  display: flex;
  align-items: center;
  padding: ${SPACE_XS} ${SPACE_LG} ${SPACE_XS} ${SPACE_LG};
  color: ${p => p.theme.textColorSnd};

  .prefix {
    flex-shrink: 0;
    margin-right: ${SPACE_XS};
    color: ${p => p.theme.textColorLight};
  }

  h4 {
    flex: 1;
    font-weight: ${FONT_WEIGHT_MEDIUM};
  }

  &.active {
    color: ${p => p.theme.primary};
    background-color: ${p => p.theme.bodyBackground};

    .prefix {
      color: ${p => p.theme.primary};
    }
  }
`;

const ThemeBadge = styled.span<{ background?: string }>`
  width: ${SPACE_TIMES(4)};
  height: ${SPACE_TIMES(4)};
  background-color: ${p => p.background || WHITE};
  border-radius: 50%;
  box-shadow: 0 0 2px 1px rgba(0, 0, 0, 0.2);
`;
