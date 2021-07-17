import React from 'react';
import useSearchStock from '../../hooks/useSearchStock';
import StockSearchInput from './StockSearchInput';
import StockSearchList from './StockSearchList';

function StockSearchPage() {
  const { searchStock, loading, stockPage, error } = useSearchStock();
  return (
    <div>
      <StockSearchInput searchStock={searchStock} />
      <StockSearchList stockPage={stockPage} loading={loading} error={error} />
    </div>
  );
}

export default StockSearchPage;
