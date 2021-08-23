import { InterestStockIds, StockDetail } from '../types/stock';
import { useState } from 'react';

function toIds(interestStocks: StockDetail[]) {
  const interestStockIds: InterestStockIds = {};
  interestStocks.forEach(stockDetail => interestStockIds[stockDetail.stockId] = true);
  return interestStockIds;
}

export default function useInterestStockManager(init: StockDetail[]) {
  const [interestStocks, setInterestStocks] = useState(init)
  const addInterestStock = (interestStock: StockDetail) => {
    setInterestStocks([...interestStocks, interestStock]);
  }
  const removeInterestStock = (interestStock: StockDetail) => {
    setInterestStocks(interestStocks.filter(stockDetail => stockDetail.stockId !== interestStock.stockId));
  }

  // TODO
  const saveInterestStocks = (): Promise<void> => {
    console.log('save: ', interestStocks);
    return Promise.resolve();
  }


  return {
    interestStocks,
    interestStockIds: toIds(interestStocks),
    addInterestStock,
    removeInterestStock,
    saveInterestStocks
  }
}
