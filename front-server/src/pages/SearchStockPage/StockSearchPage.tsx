import React from 'react';
import useSearchStock from '../../hooks/useSearchStock';
import StockSearchInput from '../../components/StockSearchInput';
import StockSearchList from './StockSearchList';

function StockSearchPage() {
  const { searchStock, loading, stockPage, error } = useSearchStock(true);
  return (
    <div style={{ marginTop: 18 }}>
      <StockSearchInput searchStock={searchStock} />
      <StockSearchList stockPage={stockPage} loading={loading} error={error} />
    </div>
  );
}

export default StockSearchPage;
