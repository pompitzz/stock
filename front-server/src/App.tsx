import React, { useState } from 'react';
import { createTheme, CssBaseline, MuiThemeProvider } from '@material-ui/core';
import MainTemplate from './components/MainTemplate';
import { routes } from './routes/routes';

const THEME_KEY = 'theme';

export enum ThemeType {
  DARK = 'dark',
  LIGHT = 'light'
}

const findTheme = () => localStorage.getItem(THEME_KEY) === ThemeType.DARK ? ThemeType.DARK : ThemeType.LIGHT;
const saveTheme = (themeType: ThemeType) => localStorage.setItem(THEME_KEY, themeType);

function App() {
  const [themeType, setThemeType] = useState(findTheme());
  const changeTheme = () => {
    const themeToBeChanged = themeType === ThemeType.LIGHT ? ThemeType.DARK : ThemeType.LIGHT;
    setThemeType(themeToBeChanged);
    saveTheme(themeToBeChanged);
  };
  const theme = createTheme({
    palette: {
      type: themeType,
    }
  });
  return (
    <MuiThemeProvider theme={theme}>
      <CssBaseline />
      <MainTemplate routes={routes} changeTheme={changeTheme} themeType={themeType} />
    </MuiThemeProvider>
  );
}

export default App;
