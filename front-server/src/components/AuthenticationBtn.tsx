import React from 'react';
import { Button } from '@material-ui/core';
import { makeStyles, Theme } from '@material-ui/core/styles';
import { kakaoLogin } from '../lib/auth/kakao/kakao';
import LastViewedPageHolder from '../lib/auth/LastViewedPageHolder';

const useStyles = makeStyles((theme: Theme) => ({
  login: {
    color: '#ffeb3b',
    borderColor: '#ffeb3b',
  }
}));

interface AuthenticationBtnProps {
  lastViewedPagePath: string;
}

function AuthenticationBtn({ lastViewedPagePath }: AuthenticationBtnProps) {
  const classes = useStyles();
  const login = () => {
    kakaoLogin();
    LastViewedPageHolder.save(lastViewedPagePath);
  }
  return (
    <Button className={classes.login} variant="outlined" onClick={login}>KAKAO LOGIN</Button>
  );
}

export default AuthenticationBtn;
