import { Currency } from './common';

export type StockContext = {
  stockDetail: StockDetail;
  interest: boolean;
  historicalPrices: HistoricalPrice[];
};

export type StockDetail = {
  stockId: number;
  symbol: string;
  name: string;
  price: number;
  priceDate: string;
  currency: Currency;
}

export type HistoricalPrice = {
  date: string;
  open: number;
  high: number;
  low: number;
  close: number;
  volume: number;
}

export class PeriodType {
  constructor(public readonly name: string,
              public readonly desc: string) {
  }

  static W_1 = new PeriodType('W_1', '1 week');
  static W_2 = new PeriodType('W_2', '2 weeks');
  static W_3 = new PeriodType('W_3', '3 weeks');
  static M_1 = new PeriodType('M_1', '1 month');
  static M_3 = new PeriodType('M_3', '3 months');
  static M_6 = new PeriodType('M_6', '6 months');
  static Y_1 = new PeriodType('Y_1', '1 year');
  static Y_3 = new PeriodType('Y_3', '3 years');
  static Y_5 = new PeriodType('Y_5', '5 years');
  static Y_10 = new PeriodType('Y_10', '10 years');
  static TYPES = [PeriodType.W_1, PeriodType.W_2, PeriodType.W_3, PeriodType.M_1, PeriodType.M_3, PeriodType.M_6, PeriodType.Y_1, PeriodType.Y_3, PeriodType.Y_5, PeriodType.Y_10]

  static findByName(name: string): PeriodType {
    const periodType = this.TYPES.find(periodType => periodType.name === name);
    if (!periodType) {
      throw new Error(`can not find period type by name. name:${name}`);
    }
    return periodType;
  }
}

// enum class StockSearchPeriodType(
//   val desc: String,
//   val selectionCycle: StockSearchSelectionCycle,
// ) {










