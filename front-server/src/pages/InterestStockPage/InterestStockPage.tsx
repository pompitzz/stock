import React from 'react';
import { withRouter } from 'react-router-dom';
import InterestStockEditor from './InterestStockEditor';
import useFindInterestStocks from '../../hooks/useFindInterestStocks';
import { CircularProgress } from '@material-ui/core';
import { makeStyles, Theme } from '@material-ui/core/styles';

const useStyles = makeStyles((theme: Theme) => ({
    loading: {
      textAlign: 'center',
      marginTop: theme.spacing(2),
      marginBottom: theme.spacing(2),
    },
  })
);

function InterestStockPage() {
  const { interestStocks, loading, error, findInterestStocks } = useFindInterestStocks()
  const classes = useStyles();
  if (loading) {
    return <div className={classes.loading}><CircularProgress /></div>;
  }
  return (
    <div>
      <InterestStockEditor parentInterestStocks={interestStocks} findInterestStocks={findInterestStocks}/>
    </div>
  );
}

export default withRouter(InterestStockPage);
