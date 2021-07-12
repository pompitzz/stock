import { useDispatch, useSelector } from 'react-redux';
import { ChangeEvent, useEffect } from 'react';
import { searchStockAction } from '../modules/stock';
import PageInfo, { Page } from '../types/pages';
import { RootState } from '../modules';

export default function useSearchStock() {
  const dispatch = useDispatch();

  const { loading, stockPage, error } = useSelector(({ stock }: RootState) => ({
    loading: stock.stockSearchResult.loading,
    stockPage: stock.stockSearchResult.data || Page.empty(),
    error: stock.stockSearchResult.error
  }));

  const firstPageRequest = PageInfo.emptyPageInfo().toPageRequest();
  useEffect(() => {
    dispatch(searchStockAction.request({
      query: '',
      pageRequest: firstPageRequest,
    }));
  }, [dispatch]);

  const searchStock = (e: ChangeEvent<HTMLInputElement>) => {
    dispatch(searchStockAction.request({
      query: e.target.value,
      pageRequest: firstPageRequest,
    }));
  };
  // TODO: add changePageEvent
  return { searchStock, stockPage, loading, error }
}
