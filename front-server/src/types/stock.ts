import { Currency } from './common';

export type StockContext = {
  stockDetail: StockDetail;
  interest: boolean;
};

export type StockDetail = {
  stockId: number;
  symbol: string;
  name: string;
  price: number;
  priceDate: string;
  currency: Currency;
  interest: boolean;
}
