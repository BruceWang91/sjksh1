import {
  FolderAddFilled,
  FundProjectionScreenOutlined,
  MenuUnfoldOutlined,
} from '@ant-design/icons';
import { ListSwitch } from 'app/components';
import useI18NPrefix, { I18NComponentProps } from 'app/hooks/useI18NPrefix';
import classnames from 'classnames';
import { memo, useCallback, useEffect, useMemo, useState } from 'react';
import { useSelector } from 'react-redux';
import { useRouteMatch } from 'react-router';
import styled from 'styled-components/macro';
import { LEVEL_5, SPACE_TIMES } from 'styles/StyleConstants';
import { selectStoryboards, selectVizs } from '../slice/selectors';
import { Folder } from '../slice/types';
import { Folders } from './Folders';
import { Storyboards } from './Storyboards';

interface SidebarProps extends I18NComponentProps {
  isDragging: boolean;
  width: number;
  sliderVisible: boolean;
  handleSliderVisible: (status: boolean) => void;
  vizDatatype:string,
}

export const Sidebar = memo(
  ({
    width,
    isDragging,
    i18nPrefix,
    sliderVisible,
    handleSliderVisible,
    vizDatatype
  }: SidebarProps) => {
    const [selectedKey, setSelectedKey] = useState('folder');
    const vizs = useSelector(selectVizs);
    const storyboards = useSelector(selectStoryboards);

    const matchDetail = useRouteMatch<{ vizId: string }>(
      '/organizations/:orgId/'+vizDatatype+'/:vizId',
    );
    const vizId = matchDetail?.params.vizId;
    const t = useI18NPrefix(i18nPrefix);
    const selectedFolderId = useMemo(() => {
      if (vizId && vizs) {
        const viz = vizs.find(({ relId }) => relId === vizId);
        return viz && viz.id;
      }
    }, [vizId, vizs]);

    useEffect(() => {
      if (vizId) {
        const viz =
          vizs.find(({ relId }) => relId === vizId) ||
          storyboards.find(({ id }) => id === vizId);
        if (viz) {
          setSelectedKey((viz as Folder).relType ? 'folder' : 'presentation');
        }
      }
    }, [vizId, storyboards, vizs]); // just switch when vizId changed

    // const listTitles = useMemo(
    //   () => [
    //     { key: 'folder', icon: <FolderAddFilled />, text: t('folder') },
    //     {
    //       key: 'presentation',
    //       icon: <FundProjectionScreenOutlined />,
    //       text: t('presentation'),
    //     },
    //   ],
    //   [t],
    // );
    //
    // const switchSelect = useCallback(key => {
    //   setSelectedKey(key);
    // }, []);

    return (
      <Wrapper
        slidervisible={(sliderVisible as Boolean).toString()}
        className={sliderVisible ? 'close' : ''}
        isDragging={isDragging}
        width={width}
      >
        {sliderVisible ? (
          <MenuUnfoldOutlined className="menuUnfoldOutlined" />
        ) : (
          ''
        )}
        {/*<ListSwitch*/}
        {/*  titles={listTitles}*/}
        {/*  selectedKey={selectedKey}*/}
        {/*  onSelect={switchSelect}*/}
        {/*/>*/}
        <Folders
          sliderVisible={sliderVisible}
          handleSliderVisible={handleSliderVisible}
          selectedId={selectedFolderId}
          i18nPrefix={i18nPrefix}
          vizDatatype={vizDatatype}
          className={classnames({ hidden: selectedKey !== 'folder' })}
        />
        {/*<Storyboards*/}
        {/*  sliderVisible={sliderVisible}*/}
        {/*  handleSliderVisible={handleSliderVisible}*/}
        {/*  selectedId={vizId}*/}
        {/*  className={classnames({ hidden: selectedKey !== 'presentation' })}*/}
        {/*  i18nPrefix={i18nPrefix}*/}
        {/*/>*/}
      </Wrapper>
    );
  },
);

const Wrapper = styled.div`
  z-index: ${LEVEL_5};
  display: flex;
  flex-direction: column;
  flex-shrink: 0;
  min-height: 0;
  border-color:${p => p.theme.borderColorSplit};
  border-style: solid;
  border-width:1px 0;
  background: ${p => p.theme.siderBackground};
  box-shadow: ${p => p.theme.shadowSider};
  transition: ${p => (!p.isDragging ? 'width 0.3s ease' : 'none')};
  .hidden {
    display: none;
  }
  > ul {
    display: ${p => (p.slidervisible === 'true' ? 'none' : 'block')};
  }
  > div {
    display: ${p => (p.slidervisible === 'true' ? 'none' : 'flex')};
  }
  &.close {
    position: absolute;
    width: ${SPACE_TIMES(7.5)} !important;
    height: calc( 100% - ${SPACE_TIMES(13)} ) !important ;
    .menuUnfoldOutlined {
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
    }
    &:hover {
      width: ${p => p.width + '%'} !important;
      .menuUnfoldOutlined {
        display: none;
      }
      > ul {
        display: block;
      }
      > div {
        display: flex;
        &.hidden {
          display: none;
        }
      }
    }
  }


  .ant-tree{
  	background-color:transparent;
  }
`;
