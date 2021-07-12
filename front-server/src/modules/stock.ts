import { createApiAction } from './utils/actionUtils';
import { debounce, takeLatest } from 'redux-saga/effects'
import createAsyncSaga from './utils/sagaUtils';
import stockApi from '../lib/api/stockApi';
import { Page } from '../types/pages';
import { AxiosResponse } from 'axios';
import { apiState, ApiState, createApiReducer } from './utils/reducerUtils';
import { FindStockContextRequest, SearchRequest } from '../types/common';
import { StockContext } from '../types/stock';
import { createReducer } from 'typesafe-actions';

const SEARCH_STOCK = 'stock/search';
export const searchStockAction = createApiAction(SEARCH_STOCK)<SearchRequest, Page<StockContext>, AxiosResponse>();

const FIND_CONTEXT_BY_SYMBOL = 'stock/findContextBySymbol';
export const findStockContextAction = createApiAction(FIND_CONTEXT_BY_SYMBOL)<FindStockContextRequest, StockContext, AxiosResponse>();

export function* stockSaga() {
  yield debounce(300, SEARCH_STOCK, createAsyncSaga(searchStockAction, stockApi.search));
  yield takeLatest(FIND_CONTEXT_BY_SYMBOL, createAsyncSaga(findStockContextAction, stockApi.findStockContextBySymbol));
}

export type StockState = {
  stockSearchResult: ApiState<Page<StockContext>>;
  stockContext: ApiState<StockContext>;
}

const initialState: StockState = {
  stockSearchResult: apiState.initial(Page.empty()),
  stockContext: apiState.initial(),
};

export default createReducer<StockState>(initialState, {
  ...createApiReducer(searchStockAction, 'stockSearchResult'),
  ...createApiReducer(findStockContextAction, 'stockContext'),
});
