import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../modules';
import { LoginRequest } from '../types/auth';
import { issueTokenAction, logoutAction } from '../modules/auth';
import { useEffect } from 'react';
import tokenService from '../lib/auth/TokenService';
import authApi from '../lib/api/authApi';
import { AxiosError } from 'axios';
import useIsLoggedIn from './useIsLoggedIn';

export default function useAuth() {
  const dispatch = useDispatch();
  const { token, error } = useSelector(({ auth }: RootState) => ({
    token: auth.jwtToken.data?.token,
    error: auth.jwtToken.error,
  }));
  const isLoggedIn: boolean = useIsLoggedIn();

  const issueToken = (loginRequest: LoginRequest) => {
    dispatch(issueTokenAction.request(loginRequest));
  };

  const logout = () => {
    tokenService.removeToken();
    dispatch(logoutAction());
  }

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
  });
  return {
    issueToken,
    token,
    error,
    isLoggedIn,
    logout,
  }
}
