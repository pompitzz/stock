import { useDispatch, useSelector } from 'react-redux';
import { ChangeEvent, useEffect } from 'react';
import { stockSearchAction } from '../modules/stock';
import PageInfo, { Page } from '../types/pages';
import { RootState } from '../modules';

export default function useStockSearch() {
  const dispatch = useDispatch();

  const { loading, stockPage, error } = useSelector(({ stock }: RootState) => ({
    loading: stock.stockSummaryPageApiState.loading,
    stockPage: stock.stockSummaryPageApiState.data || Page.empty(),
    error: stock.stockSummaryPageApiState.error
  }));

  const firstPageRequest = PageInfo.emptyPageInfo().toPageRequest();
  useEffect(() => {
    dispatch(stockSearchAction.request({
      query: '',
      pageRequest: firstPageRequest,
    }));
  }, [dispatch]);

  const searchStock = (e: ChangeEvent<HTMLInputElement>) => {
    dispatch(stockSearchAction.request({
      query: e.target.value,
      pageRequest: firstPageRequest,
    }));
  };
  // TODO: add changePageEvent
  return { searchStock, stockPage, loading, error }
}
