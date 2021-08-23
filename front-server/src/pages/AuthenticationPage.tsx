import React, { useEffect } from 'react';
import qs from 'qs';
import { RouteComponentProps, withRouter } from 'react-router-dom';
import { makeStyles, Theme } from '@material-ui/core/styles';
import { CircularProgress, Typography } from '@material-ui/core';
import { KAKAO } from '../lib/auth/kakao/kakao';
import useAuth from '../hooks/useAuth';
import tokenService from '../lib/auth/TokenService';
import LastViewedPageHolder from '../lib/auth/LastViewedPageHolder';

const useStyles = makeStyles((theme: Theme) => ({
  root: {
    width: '100%',
    height: '100%',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center'
  }
}));

function AuthenticationPage({ location, history }: RouteComponentProps) {
  const classes = useStyles();
  const { issueToken, token, error } = useAuth();
  const { code } = qs.parse(location.search, {
    ignoreQueryPrefix: true,
  });
  useEffect(() => {
    issueToken({
      code: code as string,
      redirectUrl: KAKAO.REDIRECT_URL
    })
  })
  useEffect(() => {
    if (token) {
      tokenService.saveToken(token);
      const lastViewedPagePath = LastViewedPageHolder.getAndRemove() || '/search-stock';
      history.push(lastViewedPagePath);
    }
  }, [token]);

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
