import React from 'react';
import { Button } from '@material-ui/core';
import { makeStyles, Theme } from '@material-ui/core/styles';
import { kakaoLogin } from '../lib/auth/kakao/kakao';
import LastViewedPageHolder from '../lib/auth/LastViewedPageHolder';
import { RouteComponentProps } from 'react-router-dom';
import useLogin from '../hooks/useLogin';

const useStyles = makeStyles((theme: Theme) => ({
  login: {
    color: '#ffeb3b',
    borderColor: '#ffeb3b',
  }
}));

interface AuthenticationBtnProps {
  lastViewedPagePath: string;
  routeProps: RouteComponentProps;
}

function AuthenticationBtn({ lastViewedPagePath, routeProps }: AuthenticationBtnProps) {
  const classes = useStyles();
  const { isLoggedIn, logout } = useLogin(routeProps);
  const startOAuthLogin = () => {
    LastViewedPageHolder.save(lastViewedPagePath);
    kakaoLogin();
  }
  const onLogout = () => {
    logout();
    routeProps.history.push('/search-stock');
  }

  if (isLoggedIn) {
    return <Button className={classes.login} variant="outlined" onClick={onLogout}>LOGOUT</Button>
  }
  return (
    <Button className={classes.login} variant="outlined" onClick={startOAuthLogin}>LOGIN</Button>
  );
}

export default AuthenticationBtn;
