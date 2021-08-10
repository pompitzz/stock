import React, { useEffect } from 'react';
import qs from 'qs';
import { RouteComponentProps, withRouter } from 'react-router-dom';
import { makeStyles, Theme } from '@material-ui/core/styles';
import { CircularProgress, Typography } from '@material-ui/core';
import { KAKAO } from '../lib/auth/kakao/kakao';
import useLogin from '../hooks/useLogin';

const useStyles = makeStyles((theme: Theme) => ({
  root: {
    width: '100%',
    height: '100%',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center'
  }
}));

function AuthenticationPage(props: RouteComponentProps) {
  const classes = useStyles();
  const { login, error } = useLogin(props);
  const { code } = qs.parse(props.location.search, {
    ignoreQueryPrefix: true,
  });
  useEffect(() => {
    login({
      code: code as string,
      redirectUrl: KAKAO.REDIRECT_URL
    })
  }, [])

  if (error) {
    return (
      <Typography className={classes.root} variant="h5">로그인 실패</Typography>
    )
  }
  return (
    <div className={classes.root}>
      <CircularProgress size={80} />
    </div>
  );
}

export default withRouter(AuthenticationPage);
