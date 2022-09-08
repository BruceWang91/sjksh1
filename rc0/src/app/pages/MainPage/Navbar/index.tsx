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
  FunctionOutlined,
  GlobalOutlined,
  ProfileOutlined,
  SafetyCertificateFilled,
  SettingFilled,
  SettingOutlined,
  SkinOutlined,
  UserOutlined,
  ClusterOutlined,

  FileExcelOutlined,
  FolderOutlined,
  FileZipOutlined,
  DatabaseOutlined,
  FileImageOutlined,
  PrinterOutlined,

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
import { Access } from '../Access';
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


  const subnavs = useMemo(
    () => {
    	const settings = [
	      {
	        name: 'variables',
	        title: t('settingSubnavs.variables.title'),
	        icon: <FunctionOutlined />,
	        module: ResourceTypes.Manager,
	      },
	      {
	        name: 'orgSettings',
	        title: t('settingSubnavs.orgSettings.title'),
	        icon: <SettingOutlined />,
	        module: ResourceTypes.Manager,
	      },
	      {
	        name: 'resourceMigration',
	        title: t('settingSubnavs.resourceMigration.title'),
	        icon: <ExportOutlined />,
	        module: ResourceTypes.Manager,
	      },
	    ];
	    const excelTemplates = [
	      {
	        name: 'excelManager',
	        title: t('excelTemplateSubnavs.excelManager.title'),
	        icon: <FileExcelOutlined />,
	        module: ResourceTypes.ExcelTemplate,
	      },
	      {
	        name: 'reportSheets',
	        title: t('excelTemplateSubnavs.reportSheets.title'),
	        icon: <FileZipOutlined />,
	        module: ResourceTypes.ExcelTemplate,
	      },
	      {
	        name: 'categoryManager',
	        title: t('excelTemplateSubnavs.categoryManager.title'),
	        icon: <FolderOutlined />,
	        module: ResourceTypes.ExcelTemplate,
	      }
	    ]
	    const departments = [
	      {
	        name: 'departmentManager',
	        title: t('departmentSubnavs.departmentManager.title'),
	        icon: <FunctionOutlined />,
	        module: ResourceTypes.Department,
	      }
	    ]

	    const reports = [
	      {
	        name: 'dataReport',
	        title: t('reportSubnavs.dataReport.title'),
	        icon: <DatabaseOutlined />,
	        module: ResourceTypes.Report,
	      },
	      {
	        name: 'chartReport',
	        title: t('reportSubnavs.chartReport.title'),
	        icon: <FileImageOutlined />,
	        module: ResourceTypes.Report,
	      },
	      {
	        name: 'printReport',
	        title: t('reportSubnavs.printReport.title'),
	        icon: <PrinterOutlined />,
	        module: ResourceTypes.Report,
	      }
	    ]
    	const subnavOptions = [settings,excelTemplates,departments,reports]

    	const index = subnavOptions.findIndex( item=> item.some(({name})=>name === matchModules?.params.moduleName));

    	return {
    		current:subnavOptions[index] || [],
    		data:{
    			departments,
    			reports,
    			excelTemplates,
    			settings
    		}
    	}
    },
    [t,matchModules],
  );


  const navs = useMemo(
    () => [

      {
        name: 'vizs',
        title: t('nav.vizs'),
        icon: <i className="iconfont icon-xietongzhihuidaping" />,
        module: ResourceTypes.Viz,
      },

      {
        name: 'views',
        title: t('nav.views'),
        icon: <i className="iconfont icon-24gf-table" />,
        module: ResourceTypes.View,
      },
      {
        name: 'sources',
        title: t('nav.sources'),
        icon: <i className="iconfont icon-shujukupeizhi" />,
        module: ResourceTypes.Source,
      },
      {
        name: 'schedules',
        title: t('nav.schedules'),
        icon: <i className="iconfont icon-fasongyoujian" />,
        module: ResourceTypes.Schedule,
      },

      {
        name: 'reports',
        action:'toSub',
        title: t('nav.reports'),
        icon: <ProfileOutlined />,
        isActive: (_, location) => {
          const reg = new RegExp(
            `\\/organizations\\/[\\w]{32}\\/(${subnavs.data.reports
              .map(({ name }) => name)
              .join('|')})`,
          );
          return !!location.pathname.match(reg);
        },
        module: ResourceTypes.Report,
      },

      {
        name: 'excelTemplates',
        action:'toSub',
        title: t('nav.exceltemplates'),
        icon: <TableOutlined />,
        isActive: (_, location) => {
          const reg = new RegExp(
            `\\/organizations\\/[\\w]{32}\\/(${subnavs.data.excelTemplates
              .map(({ name }) => name)
              .join('|')})`,
          );
          return !!location.pathname.match(reg);
        },
        module: ResourceTypes.ExcelTemplate,
      },

      {
        name: 'departments',
        action:'toSub',
        title: t('nav.departments'),
        icon: <ClusterOutlined />,
        isActive: (_, location) => {

          const reg = new RegExp(
            `\\/organizations\\/[\\w]{32}\\/(${subnavs.data.departments
              .map(({ name }) => name)
              .join('|')})`,
          );

          return !!location.pathname.match(reg);
        },
        module: ResourceTypes.Department,
      },
      {
        name: 'members',
        title: t('nav.members'),
        icon: <i className="iconfont icon-users1" />,
        isActive: (_, location) =>
          !!location.pathname.match(
            /\/organizations\/[\w]{32}\/(members|roles)/,
          ),
        module: ResourceTypes.User,
      },
      {
        name: 'permissions',
        title: t('nav.permissions'),
        icon: <SafetyCertificateFilled />,
        module: ResourceTypes.Manager,
      },
      {
        name: 'settings',
        action:'toSub',
        title: t('nav.settings'),
        icon: <SettingFilled />,
        isActive: (_, location) => {
          const reg = new RegExp(
            `\\/organizations\\/[\\w]{32}\\/(${subnavs.data.settings
              .map(({ name }) => name)
              .join('|')})`,
          );
          return !!location.pathname.match(reg);
        },
        module: ResourceTypes.Manager,
      },
    ],
    [subnavs, t],
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

  return (
    <>
      <MainNav>
        <Brand onClick={brandClick}>
          <img src={logo} alt="logo" />
          <LogoText size={42} />

        </Brand>
        <Nav>
          {navs.map(({ name, action, title, icon, isActive, module }) => {


            return action !== 'toSub' || subnavs.data[name]?.length > 0 ? (
              <Access
                key={name}
                type="module"
                module={module}
                level={PermissionLevels.Enable}
              >
                <NavItem
                  to={`/organizations/${orgId}/${
                    action === 'toSub' ? subnavs.data[name]?.[0]?.name : name
                  }`}
                  activeClassName="active"
                  {...(isActive && { isActive })}
                >
                  {icon} <span>{title}</span>
                </NavItem>

              </Access>
            ) : null;
          })}
        </Nav>
        <Toolbar>
          <DownloadListPopup
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
          />
          {systemInfo?.tenantManagementMode ===
            TenantManagementMode.Platform && (
            <Popup
              content={<OrganizationList />}
              trigger={['click']}
              placement="bottomRight"
              onVisibleChange={organizationListVisibleChange}
            >
              <li>
                <Tooltip title={t('nav.organization.title')} placement="right">
                  <Avatar
                    src={`${BASE_RESOURCE_URL}${currentOrganization?.avatar}`}
                  >
                    <BankFilled />
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
                {/*<MenuListItem*/}
                {/*  key="language"*/}
                {/*  prefix={<GlobalOutlined className="icon" />}*/}
                {/*  title={<p>{t('nav.account.switchLanguage.title')}</p>}*/}
                {/*  sub*/}
                {/*>*/}
                {/*  <MenuListItem key="zh">中文</MenuListItem>*/}
                {/*  <MenuListItem key="en">English</MenuListItem>*/}
                {/*</MenuListItem>*/}
                {/*<MenuListItem*/}
                {/*  key="theme"*/}
                {/*  prefix={<SkinOutlined className="icon" />}*/}
                {/*  title={<p>{t('nav.account.switchTheme.title')}</p>}*/}
                {/*  sub*/}
                {/*>*/}
                {/*  <MenuListItem key="light" prefix={<ThemeBadge />}>*/}
                {/*    {t('nav.account.switchTheme.light')}*/}
                {/*  </MenuListItem>*/}
                {/*  <MenuListItem*/}
                {/*    key="dark"*/}
                {/*    prefix={<ThemeBadge background={BLACK} />}*/}
                {/*  >*/}
                {/*    {t('nav.account.switchTheme.dark')}*/}
                {/*  </MenuListItem>*/}
                {/*</MenuListItem>*/}
                <Menu.Divider />
                <MenuListItem
                  key="profile"
                  prefix={<ProfileOutlined className="icon" />}
                >
                  <p>{t('nav.account.profile.title')}</p>
                </MenuListItem>
                <MenuListItem
                  key="password"
                  prefix={<FormOutlined className="icon" />}
                >
                  <p>{t('nav.account.changePassword.title')}</p>
                </MenuListItem>
                <MenuListItem
                  key="logout"
                  prefix={<ExportOutlined className="icon" />}
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
                <UserOutlined />
              </Avatar>
            </li>
          </Popup>
        </Toolbar>
        <Profile visible={profileVisible} onCancel={hideProfile} />
        <ModifyPassword
          visible={modifyPasswordVisible}
          onCancel={hideModifyPassword}
        />
      </MainNav>
      {subnavs.current.length > 0 && (
        <SubNav>
          <List
            dataSource={ subnavs.current }
            renderItem={({ name, title, icon }) => (
              <SubNavTitle
                key={name}
                to={`/organizations/${orgId}/${name}`}
                activeClassName="active"
              >
                {cloneElement(icon, { className: 'prefix' })}
                <h4>{title}</h4>
              </SubNavTitle>
            )}
          />
        </SubNav>
      )}
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
  height:${SPACE_TIMES(12)};
  background-color: ${p => p.theme.componentBackground};
  border-bottom: 1px solid ${p => p.theme.borderColorSplit};
`;

const Brand = styled.div`
  display: flex;
  flex-shrink: 0;
  align-items: center;
  justify-content: center;
  height: ${SPACE_TIMES(12)};

  cursor: pointer;


  color: ${p => p.theme.textColor};
   img {
   	margin:0 6px;
    width: ${SPACE_TIMES(9)};
    height: ${SPACE_TIMES(9)};
  }

`;

const Nav = styled.nav`
  display: flex;
  flex: 1;
  flex-direction: row;
  padding: 0 ${SPACE_LG};
  align-items: center;
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
