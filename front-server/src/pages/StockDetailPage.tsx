import React from 'react';
import { RouteComponentProps, withRouter } from 'react-router-dom';
import useFindStockContext from '../hooks/useFindStockContext';
import HistoricalPriceChart from '../components/HistoricalPriceChart';
import PeriodSelect from '../components/PeriodSelect';
import { makeStyles, Theme } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import { CircularProgress } from '@material-ui/core';
import HistoricalPricesTable from '../components/HistoricalPricesTable';

const useStyles = makeStyles((theme: Theme) => ({
  root: {
    padding: theme.spacing(3)
  },
  loading: {
    height: '100%',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center'
  },
  container: {
    maxWidth: 1000,
    marginLeft: 'auto',
    marginRight: 'auto'
  },
  detail: {
    display: 'flex',
    justifyContent: 'space-between',
    paddingLeft: theme.spacing(2),
    paddingRight: theme.spacing(2)
  },
  chart: {},
  table: {
    paddingTop: theme.spacing(2),
    paddingBottom: theme.spacing(2)
  },
}));

function StockDetailPage({ match }: RouteComponentProps<{ symbol: string }>) {
  const classes = useStyles();
  const { symbol } = match.params;
  const { stockContext, loading, error, setPeriodType, periodType } = useFindStockContext(symbol);

  if (error) {
    return <Typography className={classes.loading} variant="h5">something is wrong :( {error}</Typography>;
  }
  if (loading || !stockContext) {
    return <div className={classes.loading}><CircularProgress size={60} /></div>;
  }
  const { stockDetail, historicalPrices } = stockContext;
  const { stockId, name, price, priceDate, currency } = stockDetail;
  return (
    <div className={classes.root}>
      <div className={classes.container}>
        <div className={classes.detail}>
          <Typography variant="h4" noWrap>
            {name}({symbol})
          </Typography>
          <div>
            <Typography variant="h5">
              {price} {currency}
            </Typography>
            <Typography variant="subtitle2" color="textSecondary">
              {priceDate}
            </Typography>
          </div>
        </div>
        <div className={classes.chart}>
          <PeriodSelect selectPeriodType={setPeriodType} currentPeriodType={periodType} />
          <HistoricalPriceChart historicalPrices={historicalPrices} detailChart />
        </div>
        <div className={classes.table}>
          <HistoricalPricesTable historicalPrices={[...historicalPrices]} />
        </div>
      </div>
    </div>
  );
}

export default withRouter(StockDetailPage);
