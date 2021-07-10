import React, { useEffect } from 'react';
import { stockSearchAction } from '../modules/stock';
import PageInfo, { Page } from '../types/pages';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../modules';

function SearchStockPage() {
  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(stockSearchAction.request({
      query: '',
      pageInfo: PageInfo.emptyPageInfo(),
    }));
  }, [dispatch]);
  const { loading, page } = useSelector(({ stock }: RootState) => ({
    loading: stock.stockSummaryPageApiState.loading,
    page: stock.stockSummaryPageApiState.data || Page.empty(),
    error: stock.stockSummaryPageApiState.error
  }));
  return (
    <div>
      <div>
        loading: {String(loading)}
      </div>
      <div>
        page: {JSON.stringify(page)}
      </div>
    </div>
  );
}

export default SearchStockPage;
