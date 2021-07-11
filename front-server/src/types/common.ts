import { PageRequest } from './pages';

export enum Currency {
  USD = 'USD',
  KRW = 'KRW'
}

export type SearchRequest = {
  query: string,
  pageRequest: PageRequest,
}
