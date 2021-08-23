import React from 'react';
import { Page } from '../../types/pages';
import { InterestStockIds, StockContext, StockDetail } from '../../types/stock';
import { CircularProgress } from '@material-ui/core';
import { makeStyles, Theme } from '@material-ui/core/styles';
import InterestStockList from './InterestStockList';

const useStyles = makeStyles((theme: Theme) => ({
    loading: {
      textAlign: 'center',
      marginTop: theme.spacing(2),
      marginBottom: theme.spacing(2),
    },
    empty: {
      textAlign: 'center',
      marginTop: theme.spacing(2),
      marginBottom: theme.spacing(2),
      fontWeight: 'bold',
      fontSize: '20px'
    },
    root: {
      marginTop: theme.spacing(1),
      maxHeight: '300px',
      overflow: 'scroll'
    },
    input: {
      marginLeft: theme.spacing(1),
      flex: 1,
    },
    iconButton: {
      padding: 10,
    },
  })
);

interface StockSearchListProps {
  stockPage: Page<StockContext>;
  loading: boolean;
  error: Error | null;
  addInterestStock: (interestStock: StockDetail) => void;
  interestStockIds: InterestStockIds;
}

function InterestStockSearchList({ stockPage, loading, error, addInterestStock, interestStockIds }: StockSearchListProps) {
  const classes = useStyles();
  if (loading) {
    return <div className={classes.loading}><CircularProgress /></div>;
  }
  if (error) {
    return <div className={classes.empty}>{error.message}</div>;
  }
  const stockDetails = stockPage.content
    .map(({ stockDetail }) => stockDetail)
    .filter((stockDetail) => !interestStockIds[stockDetail.stockId]);

  if (stockDetails.length === 0) {
    return <div className={classes.empty}>no results.</div>
  }
  return <InterestStockList stockDetails={stockDetails} interest={false} action={addInterestStock} />;
}

export default InterestStockSearchList;
