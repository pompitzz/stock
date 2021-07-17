import React from 'react';
import { createStyles, makeStyles, Theme } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import Typography from '@material-ui/core/Typography';
import { StockContext } from '../types/stock';
import { CardActionArea } from '@material-ui/core';
import { Link } from 'react-router-dom';
import HistoricalPriceChart from './HistoricalPriceChart';

const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    root: {
      maxWidth: 600,
      marginLeft: 'auto',
      marginRight: 'auto',
      display: 'flex',
    },
    wrapper: {
      display: 'flex',
      width: '100%',
      height: '100%',
      alignItems: 'stretch',
    },
    details: {
      flex: 2,
      padding: theme.spacing(2),
      overflow: 'hidden'
    },
    price: {
      flex: 1,
      padding: theme.spacing(2),
    },
    chart: {
      flex: 1,
      display: 'flex',
      alignItems: 'center',
      width: 180,
    },
  }),
);

interface StockCardProps {
  stockContext: StockContext,
}

function StockCard({ stockContext }: StockCardProps) {
  const { name, symbol, price, currency, priceDate } = stockContext.stockDetail;
  const classes = useStyles();
  return (
    <Card className={classes.root}>
      <CardActionArea
        className={classes.wrapper}
        component={Link}
        to={`/stock-detail/${symbol}`}
      >
        <div className={classes.details}>
          <Typography component="h6" variant="h6" noWrap>
            {name}
          </Typography>
          <Typography variant="subtitle1" color="textSecondary">
            {symbol}
          </Typography>
          <Typography variant="subtitle2" color="textSecondary">
            {priceDate}
          </Typography>
        </div>
        <div className={classes.price}>
          <Typography variant="subtitle1" color="textSecondary">
            {price} {currency}
          </Typography>
          {/*// TODO add diff price*/}
        </div>
        <div className={classes.chart}>
          <HistoricalPriceChart historicalPrices={stockContext.historicalPrices} />
        </div>
      </CardActionArea>
    </Card>
  );
}

export default StockCard;
