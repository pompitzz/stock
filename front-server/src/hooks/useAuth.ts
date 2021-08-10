import { useDispatch } from 'react-redux';
import { logoutAction } from '../modules/auth';
import tokenService from '../lib/auth/TokenService';
import { RouteComponentProps } from 'react-router-dom';
import { useState } from 'react';

export default function useAuth({ history }: RouteComponentProps) {
  const dispatch = useDispatch();
  const [isLoggedIn, setIsLoggedIn] = useState(tokenService.hasToken());
  const logout = () => {
    dispatch(logoutAction());
    tokenService.removeToken();
    setIsLoggedIn(false);
    history.push('/search-stock');
  }
  return {
    logout,
    isLoggedIn
  }
}
