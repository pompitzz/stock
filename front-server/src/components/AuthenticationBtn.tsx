import React from 'react';
import { Button } from '@material-ui/core';
import { makeStyles, Theme } from '@material-ui/core/styles';

const useStyles = makeStyles((theme: Theme) => ({
  login: {
    color: '#ffeb3b',
    borderColor: '#ffeb3b',
  }
}));

interface AuthenticationBtnProps {
  isLoggedIn: boolean;
  onLogin: () => void;
  onLogout: () => void;
}

function AuthenticationBtn({ isLoggedIn, onLogin, onLogout }: AuthenticationBtnProps) {
  const classes = useStyles();
  if (isLoggedIn) {
    return <Button className={classes.login} variant="outlined" onClick={onLogout}>LOGOUT</Button>
  }
  return (
    <Button className={classes.login} variant="outlined" onClick={onLogin}>LOGIN</Button>
  );
}

export default AuthenticationBtn;
