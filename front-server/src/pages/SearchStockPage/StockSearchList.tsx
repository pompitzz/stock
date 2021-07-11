import React from 'react';
import { Page } from '../../types/pages';
import { StockSummary } from '../../types/stock';
import { Box, CircularProgress } from '@material-ui/core';
import { makeStyles, Theme } from '@material-ui/core/styles';
import StockCard from '../../components/StockCard';

const useStyles = makeStyles((theme: Theme) => ({
    loading: {
      textAlign: 'center',
      marginTop: theme.spacing(5),
    },
    empty: {
      textAlign: 'center',
      marginTop: theme.spacing(5),
      fontWeight: 'bold',
      fontSize: '20px'
    },
    root: {
      paddingTop: theme.spacing(2),
      marginTop: theme.spacing(1),
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
  stockPage: Page<StockSummary>;
  loading: boolean;
  error: Error | null;
}

function StockSearchList({ stockPage, loading, error }: StockSearchListProps) {
  const classes = useStyles();
  if (loading) {
    return <div className={classes.loading}><CircularProgress /></div>;
  }
  if (error) {
    return <div className={classes.empty}>something is wrong :(</div>;
  }
  if (stockPage.content.length === 0) {
    return <div className={classes.empty}>no results.</div>
  }
  return (
    <div className={classes.root}>
      {stockPage.content.map(stock => (
        <Box pb={2}>
          <StockCard stock={stock} key={stock.stockId} />
        </Box>
      ))}
    </div>
  );
}

export default StockSearchList;