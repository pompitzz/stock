import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../modules';
import { useEffect, useState } from 'react';
import tokenService from '../lib/auth/TokenService';
import { setTokenAction } from '../modules/auth';

export default function useIsLoggedIn(): boolean {
  const dispatch = useDispatch();
  const [isLoggedIn, setIsLoggedIn] = useState<boolean>(tokenService.hasToken());
  const { token } = useSelector(({ auth }: RootState) => ({
    token: auth.jwtToken.data?.token
  }));

  useEffect(() => {
    setIsLoggedIn(!!token)
  }, [token])

  useEffect(() => {
    if (isLoggedIn && !token) {
      dispatch(setTokenAction(tokenService.getToken()));
    }
  }, [isLoggedIn, dispatch, token])
  return isLoggedIn;
}
