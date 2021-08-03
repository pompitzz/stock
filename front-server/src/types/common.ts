import { PageRequest } from './pages';
import { PeriodType } from './stock';

export enum Currency {
  USD = 'USD',
  KRW = 'KRW'
}

export type SearchRequest = {
  query: string,
  pageRequest: PageRequest,
}

export type FindStockContextRequest = {
  symbol: string,
  periodType: PeriodType,
}

export type Payload<T> = {
  payload: T;
}
