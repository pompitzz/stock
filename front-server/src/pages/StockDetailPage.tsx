import React from 'react';
import { RouteComponentProps, withRouter } from 'react-router-dom';
import useFindStockContext from '../hooks/useFindStockContext';
import HistoricalPriceChart from '../components/HistoricalPriceChart';
import PeriodSelect from '../components/PeriodSelect';

function StockDetailPage({ match }: RouteComponentProps<{ symbol: string }>) {
  const { symbol } = match.params;
  const { stockContext, loading, error, setPeriodType, periodType } = useFindStockContext(symbol);
  if (!stockContext) {
    return null;
  }
  const { stockDetail, historicalPrices } = stockContext;
  return (
    <div>
      <PeriodSelect selectPeriodType={setPeriodType} currentPeriodType={periodType} />
      <HistoricalPriceChart historicalPrices={historicalPrices} currentPeriodType={periodType} />
    </div>
  );
}

export default withRouter(StockDetailPage);
