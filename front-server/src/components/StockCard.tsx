import React from 'react';
import { createStyles, makeStyles, Theme } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import Typography from '@material-ui/core/Typography';
import { StockSummary } from '../types/stock';
import { CardActionArea } from '@material-ui/core';
import { Link } from 'react-router-dom';

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
      alignItems: 'start'
    },
    details: {
      flex: 2,
      padding: theme.spacing(2),
    },
    price: {
      flex: 1,
      padding: theme.spacing(2),
    },
    chart: {
      flex: 1,
    },
    chartImg: {
      width: '100%',
      height: '100%',
    }
  }),
);

interface StockCardProps {
  stock: StockSummary,
}

function StockCard({ stock }: StockCardProps) {
  const { name, symbol, price, currency, priceDate, stockId } = stock;
  const classes = useStyles();
  return (
    <Card className={classes.root}>
      <CardActionArea
        className={classes.wrapper}
        component={Link}
        to={`/stock-detail/${stockId}`}
      >
        <div className={classes.details}>
          <Typography component="h6" variant="h6">
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
          {/*// TODO: apply chart*/}
          <img
            className={classes.chartImg}
            src="https://cdn.pixabay.com/photo/2016/10/04/13/05/graphic-1714230_960_720.png"
            alt="preview"
          />
        </div>
      </CardActionArea>
    </Card>
  );
}

export default StockCard;
