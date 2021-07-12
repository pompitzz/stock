import { useDispatch, useSelector } from 'react-redux';
import { useEffect } from 'react';
import { findStockContextAction } from '../modules/stock';
import { PeriodType } from '../types/stock';
import { RootState } from '../modules';

export default function useFindStockContext(symbol: string) {
  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(findStockContextAction.request({ symbol, periodType: PeriodType.M_1 }))
  }, [dispatch]);

  const { stockContext, loading, error } = useSelector(({ stock }: RootState) => ({
    stockContext: stock.stockContext.data,
    loading: stock.stockContext.loading,
    error: stock.stockContext.error,
  }));
  return { stockContext, loading, error }
}
