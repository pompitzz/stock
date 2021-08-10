import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../modules';
import { LoginRequest } from '../types/auth';
import { loginAction } from '../modules/auth';
import { useEffect } from 'react';
import tokenService from '../lib/auth/TokenService';
import LastViewedPageHolder from '../lib/auth/LastViewedPageHolder';
import { RouteComponentProps } from 'react-router-dom';

export default function useLogin({ history }: RouteComponentProps) {
  const dispatch = useDispatch();
  const { token, error } = useSelector(({ auth }: RootState) => ({
    token: auth.loginResult.data?.token,
    error: auth.loginResult.error,
  }));

  const login = (loginRequest: LoginRequest) => {
    dispatch(loginAction.request(loginRequest));
  };

  useEffect(() => {
    if (token) {
      tokenService.saveToken(token);
      const lastViewedPagePath = LastViewedPageHolder.getAndRemove() || '/search-stock';
      history.push(lastViewedPagePath);
    }
  }, [token]);
  return {
    login,
    error,
    isLoggedIn: !!token
  }
}
