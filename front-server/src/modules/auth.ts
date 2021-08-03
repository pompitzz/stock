import { createAction, createReducer } from 'typesafe-actions';
import { Payload } from '../types/common';

const SET_REDIRECT_PATH = 'auth/SET_REDIRECT_PATH' as const;
export const changeField = createAction(SET_REDIRECT_PATH)<string>();

export type AuthState = {
  redirectPath: string;
}

const initialState: AuthState = {
  redirectPath: '',
};

export default createReducer<AuthState>(initialState, {
  [SET_REDIRECT_PATH]: (state: AuthState, { payload: redirectPath }: Payload<string>) => ({
    ...state,
    redirectPath: redirectPath
  }),
});
