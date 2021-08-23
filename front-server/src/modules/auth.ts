import { createAction, createReducer } from 'typesafe-actions';
import { createApiAction } from './utils/actionUtils';
import { AxiosResponse } from 'axios';
import { JwtToken, LoginRequest } from '../types/auth';
import { apiState, ApiState, createApiReducer } from './utils/reducerUtils';
import { takeLatest } from 'redux-saga/effects';
import createAsyncSaga from './utils/sagaUtils';
import authApi from '../lib/api/authApi';
import { Payload } from '../types/common';

const ISSUE_TOKEN = 'auth/issueToken';
export const issueTokenAction = createApiAction(ISSUE_TOKEN)<LoginRequest, JwtToken, AxiosResponse>();

const SET_TOKEN = 'auth/setToken';
export const setTokenAction = createAction(SET_TOKEN)<string>();

const LOGOUT = 'auth/logout' as const;
export const logoutAction = createAction(LOGOUT)<void>();

export type AuthState = {
  jwtToken: ApiState<JwtToken>;
}

export function* authSaga() {
  yield takeLatest(ISSUE_TOKEN, createAsyncSaga(issueTokenAction, authApi.loginKakao));
}

const initialState: AuthState = {
  jwtToken: apiState.initial(),
};

export default createReducer<AuthState>(initialState, {
  ...createApiReducer(issueTokenAction, 'jwtToken'),
  [LOGOUT]: (state: AuthState) => ({
    ...state,
    jwtToken: apiState.initial()
  }),
  [SET_TOKEN]: (state: AuthState, { payload: token }: Payload<string>) => ({
    ...state,
    jwtToken: apiState.initial({ token })
  }),
});
