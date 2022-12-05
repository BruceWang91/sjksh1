import { lighten, rgba } from 'polished';
import {
  BLACK,
  BLUE,
  DG10,
  DG20,
  DG30,
  DG40,
  DG50,
  DG60,
  DG70,
  DG80,
  DG90,
  ERROR,
  G10,
  G20,
  G30,
  G40,
  G50,
  G60,
  G70,
  G80,
  G90,
  GG10,
  GG20,
  GREEN,
  HIGHLIGHT,
  INFO,
  NORMAL,
  ORANGE,
  PRIMARY,
  PROCESSING,
  RED,
  SUCCESS,
  WARNING,
  WHITE,
  YELLOW,
} from '../StyleConstants';

const common = {
  primary: PRIMARY,
  info: INFO,
  success: SUCCESS,
  processing: PROCESSING,
  error: ERROR,
  highlight: HIGHLIGHT,
  warning: WARNING,
  normal: NORMAL,
  blue: BLUE,
  green: GREEN,
  orange: ORANGE,
  yellow: YELLOW,
  red: RED,
  white: WHITE,
  black: BLACK,
};

const lightTheme = {
	emphasisPrimary: rgba(PRIMARY, 0.4),
  bodyBackground: G10,
  componentBackground: WHITE,
  emphasisBackground: G20,
  highlightBackground: G30,
  tipBackground: rgba(BLACK, 0.05),
  siderBackground:GG10,
  emphasisSiderBackground:GG20,
  textColor: G90,
  textColorSnd: G80,
  textColorLight: G60,
  textColorDisabled: G50,
  iconColorHover: rgba(BLACK, 0.75),
  borderColorBase: G40,
  borderColorEmphasis: G30,
  borderColorSplit: G20,
  shadow1: `none`,
  shadow2: `0 4px 16px 0 ${rgba(lighten(0.15, BLACK), 0.12)}`,
  shadow3: `0 12px 32px 0 ${rgba(lighten(0.15, BLACK), 0.16)}`,
  shadowSider: `0px 0 32px 0px ${rgba(G70, 0.075)}`,
  shadowBlock: `0px 0 32px 0px ${rgba(G70, 0.025)}`,
  ...common,
};

const darkTheme: Theme = {
	emphasisPrimary: rgba(PRIMARY, 0.4),
  bodyBackground: BLACK,
  componentBackground: DG10,
  emphasisBackground: DG20,
  highlightBackground: DG30,
  tipBackground: rgba(WHITE, 0.05),
  siderBackground:GG10,
  emphasisSiderBackground:GG20,
  textColor: DG90,
  textColorSnd: DG80,
  textColorLight: DG60,
  textColorDisabled: DG50,
  iconColorHover: DG70,
  borderColorBase: DG40,
  borderColorEmphasis: DG30,
  borderColorSplit: DG20,
  shadow1: `0 1px 5px 0 ${rgba(BLACK, 0.1)}`,
  shadow2: `0 6px 18px 0 ${rgba(BLACK, 0.4)}`,
  shadow3: `0 10px 32px 0 ${rgba(BLACK, 0.54)}`,
  shadowSider: 'none',
  shadowBlock: 'none',
  ...common,
};

export type Theme = typeof lightTheme;

export const themes = {
  light: lightTheme,
  dark: darkTheme,
};
