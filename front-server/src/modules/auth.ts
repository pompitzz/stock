import { createAction, createReducer } from 'typesafe-actions';
import { createApiAction } from './utils/actionUtils';
import { AxiosResponse } from 'axios';
import { JwtToken, LoginRequest } from '../types/auth';
import { apiState, ApiState, createApiReducer } from './utils/reducerUtils';
import { takeLatest } from 'redux-saga/effects';
import createAsyncSaga from './utils/sagaUtils';
import authApi from '../lib/api/authApi';

const LOGIN = 'auth/login';
export const loginAction = createApiAction(LOGIN)<LoginRequest, JwtToken, AxiosResponse>();

const LOGOUT = 'auth/logout' as const;
export const logoutAction = createAction(LOGOUT)<void>();

export type AuthState = {
  loginResult: ApiState<JwtToken>;
}

export function* authSaga() {
  yield takeLatest(LOGIN, createAsyncSaga(loginAction, authApi.loginKakao));
}

const initialState: AuthState = {
  loginResult: apiState.initial(),
};

export default createReducer<AuthState>(initialState, {
  ...createApiReducer(loginAction, 'loginResult'),
  [LOGOUT]: (state: AuthState) => ({
    ...state,
    loginResult: apiState.initial()
  }),
});
