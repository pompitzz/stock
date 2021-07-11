import React from 'react';
import useStockSearch from '../../hooks/useStockSearch';
import StockSearchBox from './StockSearchBox';
import StockSearchList from './StockSearchList';

function StockSearchPage() {
  const { searchStock, loading, stockPage, error } = useStockSearch();
  return (
    <div>
      <StockSearchBox searchStock={searchStock} />
      <StockSearchList stockPage={stockPage} loading={loading} error={error} />
    </div>
  );
}

export default StockSearchPage;
