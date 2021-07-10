import { Currency } from './common';

export type StockSummary = {
  stockId: number;
  symbol: string;
  name: string;
  price: number;
  priceDate: string;
  currency: Currency;
  interest: boolean;
}
