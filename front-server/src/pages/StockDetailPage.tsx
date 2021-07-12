import React from 'react';
import { RouteComponentProps, withRouter } from 'react-router-dom';
import useFindStockContext from '../hooks/useFindStockContext';

function StockDetailPage({ match }: RouteComponentProps<{ symbol: string }>) {
  const { symbol } = match.params;
  const { stockContext, loading, error } = useFindStockContext(symbol);
  if (!stockContext) {
    return null;
  }
  const { stockDetail, historicalPrices } = stockContext;
  return (
    <div>
      {JSON.stringify(stockContext)}
    </div>
  );
}

export default withRouter(StockDetailPage);
