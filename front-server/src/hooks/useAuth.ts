import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../modules';
import { LoginRequest } from '../types/auth';
import { loginAction, logoutAction } from '../modules/auth';
import { useEffect, useState } from 'react';
import tokenService from '../lib/auth/TokenService';
import LastViewedPageHolder from '../lib/auth/LastViewedPageHolder';
import { RouteComponentProps } from 'react-router-dom';
import authApi from '../lib/api/authApi';
import { AxiosError } from 'axios';

export default function useAuth({ history }: RouteComponentProps) {
  const dispatch = useDispatch();
  const { token, error } = useSelector(({ auth }: RootState) => ({
    token: auth.loginResult.data?.token,
    error: auth.loginResult.error,
  }));
  const [isLoggedIn, setIsLoggedIn] = useState(tokenService.hasToken());

  const login = (loginRequest: LoginRequest) => {
    dispatch(loginAction.request(loginRequest));
  };

  const logout = () => {
    dispatch(logoutAction());
    tokenService.removeToken();
    setIsLoggedIn(false);
  }

  useEffect(() => {
    if (token) {
      tokenService.saveToken(token);
      setIsLoggedIn(true);
      const lastViewedPagePath = LastViewedPageHolder.getAndRemove() || '/search-stock';
      history.push(lastViewedPagePath);
    }
  }, [token]);

  useEffect(() => {
    if (isLoggedIn) {
      authApi.validateToken()
        .catch((e: AxiosError) => {
          if (e.response?.status === 401) {
            logout();
          } else {
            console.warn(e);
          }
        });
    }
  }, []);
  return {
    login,
    error,
    isLoggedIn,
    logout,
  }
}
