import { useDispatch, useSelector } from 'react-redux';
import { useEffect, useState } from 'react';
import { findStockContextAction } from '../modules/stock';
import { PeriodType } from '../types/stock';
import { RootState } from '../modules';

export default function useFindStockContext(symbol: string) {
  const dispatch = useDispatch();
  const [periodType, setPeriodType] = useState(PeriodType.M_1);
  useEffect(() => {
    dispatch(findStockContextAction.request({ symbol, periodType: periodType }))
  }, [dispatch, periodType]);

  const { stockContext, loading, error } = useSelector(({ stock }: RootState) => ({
    stockContext: stock.stockContext.data,
    loading: stock.stockContext.loading,
    error: stock.stockContext.error,
  }));
  return {
    stockContext,
    loading,
    error,
    periodType,
    setPeriodType
  }
}
