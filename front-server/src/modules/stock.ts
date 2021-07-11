import { createApiAction } from './utils/actionUtils';
import { debounce } from 'redux-saga/effects'
import createAsyncSaga from './utils/sagaUtils';
import stockApi from '../lib/api/stockApi';
import { Page } from '../types/pages';
import { AxiosResponse } from 'axios';
import { apiState, ApiState, createApiReducer } from './utils/reducerUtils';
import { SearchRequest } from '../types/common';
import { StockSummary } from '../types/stock';
import { createReducer } from 'typesafe-actions';

const SEARCH_REQUEST = 'stock/search';
export const stockSearchAction = createApiAction(SEARCH_REQUEST)<SearchRequest, Page<StockSummary>, AxiosResponse>();

export function* stockSaga() {
  yield debounce(300, SEARCH_REQUEST, createAsyncSaga(stockSearchAction, stockApi.search));
}

export type StockState = {
  stockSummaryPageApiState: ApiState<Page<StockSummary>>;
}

const initialState: StockState = {
  stockSummaryPageApiState: apiState.initial(Page.empty())
};

export default createReducer<StockState>(initialState, {
  ...createApiReducer(stockSearchAction, 'stockSummaryPageApiState')
});
