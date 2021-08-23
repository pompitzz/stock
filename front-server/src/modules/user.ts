import { createApiAction } from './utils/actionUtils';
import { takeLatest } from 'redux-saga/effects'
import createAsyncSaga from './utils/sagaUtils';
import { AxiosResponse } from 'axios';
import { apiState, ApiState, createApiReducer } from './utils/reducerUtils';
import { StockDetail } from '../types/stock';
import { createReducer } from 'typesafe-actions';
import userApi from '../lib/api/userApi';

const FIND_INTEREST_STOCKS = 'user/findInterestStocks';
export const findInterestStocksAction = createApiAction(FIND_INTEREST_STOCKS)<void, StockDetail[], AxiosResponse>();

export function* userSaga() {
  yield takeLatest(FIND_INTEREST_STOCKS, createAsyncSaga(findInterestStocksAction, userApi.findInterestStocks));
}

export type UserState = {
  interestStocks: ApiState<StockDetail[]>;
}

const initialState: UserState = {
  interestStocks: apiState.initial([]),
};

export default createReducer<UserState>(initialState, {
  ...createApiReducer(findInterestStocksAction, 'interestStocks'),
});
