import React from 'react';
import qs from 'qs';
import { RouteComponentProps, withRouter } from 'react-router-dom';
import { makeStyles, Theme } from '@material-ui/core/styles';
import { CircularProgress } from '@material-ui/core';
import LastViewedPageHolder from '../lib/auth/LastViewedPageHolder';
import { KAKAO } from '../lib/auth/kakao/kakao';

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
  // TODO: redirect url, code를 백엔드에 보내서 로그인 구현.
  const redirecturl = KAKAO.REDIRECT_URL;
  const { code } = qs.parse(location.search, {
    ignoreQueryPrefix: true,
  });
  const lastViewedPagePath = LastViewedPageHolder.getAndRemove();
  if (lastViewedPagePath) {
    history.push(lastViewedPagePath);
  }
  return (
    <div className={classes.root}>
      <CircularProgress size={80} />
    </div>
  );
}

export default withRouter(AuthenticationPage);
