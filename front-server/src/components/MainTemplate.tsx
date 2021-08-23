import React, { useMemo, useState } from 'react';
import clsx from 'clsx';
import { makeStyles, Theme } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import IconButton from '@material-ui/core/IconButton';
import MenuIcon from '@material-ui/icons/Menu';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import { Link, Route, RouteComponentProps, Switch, withRouter } from 'react-router-dom';
import { Box, Divider, Drawer, List, ListItem, ListItemIcon, ListItemText } from '@material-ui/core';
import { RouteContext, ShowInMenuOption } from '../routes/routes';
import { Brightness4, Brightness7 } from '@material-ui/icons';
import { ThemeType } from '../App';
import routeUtils from '../routes/routeUtils';
import AuthenticationBtn from './AuthenticationBtn';
import useAuth from '../hooks/useAuth';
import LastViewedPageHolder from '../lib/auth/LastViewedPageHolder';
import { kakaoLogin } from '../lib/auth/kakao/kakao';

const drawerWidth = 240;

const useStyles = makeStyles((theme: Theme) => ({
  root: {
    display: 'flex',
    flex: 1,
  },
  appBar: {
    zIndex: theme.zIndex.drawer + 1,
    transition: theme.transitions.create(['width', 'margin'], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
  },
  appBarInnerLeft: {},
  appBarInnerRight: {},
  appBarShift: {
    marginLeft: drawerWidth,
    width: `calc(100% - ${drawerWidth}px)`,
    transition: theme.transitions.create(['width', 'margin'], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen,
    }),
  },
  menuButton: {
    marginRight: 36,
  },
  hide: {
    display: 'none',
  },
  drawer: {
    width: drawerWidth,
    flexShrink: 0,
    whiteSpace: 'nowrap',
  },
  drawerOpen: {
    width: drawerWidth,
    transition: theme.transitions.create('width', {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen,
    }),
  },
  drawerClose: {
    transition: theme.transitions.create('width', {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
    overflowX: 'hidden',
    width: theme.spacing(7) + 1,
    [theme.breakpoints.up('sm')]: {
      width: theme.spacing(9) + 1,
    },
  },
  toolbar: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'flex-end',
    padding: theme.spacing(0, 1),
    ...theme.mixins.toolbar,
  },
  content: {
    display: 'flex',
    flex: 1,
    flexDirection: 'column',
  },
}));

interface MainTemplateProps extends RouteComponentProps {
  routes: RouteContext[];
  changeTheme: () => void;
  themeType: ThemeType;
}

function MainTemplate({ routes, location, changeTheme, themeType, history }: MainTemplateProps) {
  const currentRouteContext: RouteContext | undefined = useMemo(
    () => routes.find(route => routeUtils.isMatch(route, location.pathname)),
    [routes, location]
  );
  const [open, setOpen] = useState(true);
  const { isLoggedIn, logout } = useAuth();
  const onLogin = () => {
    LastViewedPageHolder.save(location.pathname);
    kakaoLogin();
  }
  const onLogout = () => {
    logout();
    history.push('/search-stock');
  }

  const handleDrawerOpen = () => {
    setOpen(true);
  };

  const handleDrawerClose = () => {
    setOpen(false);
  };

  if (currentRouteContext && currentRouteContext.showInMenuOption === ShowInMenuOption.LOGGED_IN && !isLoggedIn) {
    onLogout();
  }

  const classes = useStyles();
  return (
    <div className={classes.root}>
      <AppBar
        color="primary"
        position="fixed"
        className={clsx(classes.appBar, {
          [classes.appBarShift]: open,
        })}
      >
        <Toolbar>
          <IconButton
            color="inherit"
            aria-label="open drawer"
            onClick={handleDrawerOpen}
            edge="start"
            className={clsx(classes.menuButton, {
              [classes.hide]: open,
            })}
          >
            <MenuIcon />
          </IconButton>
          <Typography variant="h6" noWrap>
            TBD {currentRouteContext?.name && `| ${currentRouteContext.name}`}
          </Typography>
          <Box ml="auto">
            <AuthenticationBtn isLoggedIn={isLoggedIn} onLogin={onLogin} onLogout={onLogout} />
          </Box>
          <IconButton
            color={'inherit'}
            onClick={changeTheme}
          >
            {themeType === ThemeType.DARK ? <Brightness4 /> : <Brightness7 />}
          </IconButton>
        </Toolbar>
      </AppBar>
      <Drawer
        variant="permanent"
        className={clsx(classes.drawer, {
          [classes.drawerOpen]: open,
          [classes.drawerClose]: !open,
        })}
        classes={{
          paper: clsx({
            [classes.drawerOpen]: open,
            [classes.drawerClose]: !open,
          }),
        }}
      >
        <div className={classes.toolbar}>
          <IconButton onClick={handleDrawerClose}>
            <ChevronLeftIcon />
          </IconButton>
        </div>
        <Divider />
        <List>
          {routes
            .filter((context: RouteContext) => {
              if (context.showInMenuOption === ShowInMenuOption.ALWAYS) return true;
              if (isLoggedIn) return context.showInMenuOption === ShowInMenuOption.LOGGED_IN;
              return false;
            })
            .map((context: RouteContext) => (
              <ListItem button key={context.path} component={Link} to={context.path}>
                <ListItemIcon>{context.menuIconComponent}</ListItemIcon>
                <ListItemText primary={context.name} />
              </ListItem>
            ))}
        </List>
      </Drawer>
      <main className={classes.content}>
        <div className={classes.toolbar} />
        <Switch>
          {routes.map((context: RouteContext) => (
            <Route key={context.path} path={context.path}>{context.pageComponent}</Route>
          ))}
        </Switch>
      </main>
    </div>
  );
}

export default withRouter(MainTemplate);
