import { StockDetail } from '../../types/stock';
import { Add, Remove } from '@material-ui/icons';
import IconButton from '@material-ui/core/IconButton';
import React from 'react';
import { makeStyles, Theme } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';

const useStyles = makeStyles((theme: Theme) => ({
    root: {
      display: 'flex',
      alignItems: 'center',
      paddingLeft: theme.spacing(2),
      paddingRight: theme.spacing(1),
    },
    info: {
      width: '50%'
    },
    price: {
      marginLeft: theme.spacing(1)
    },
    iconBtn: {
      marginLeft: 'auto'
    }
  })
);

interface InterestStockProps {
  interest: boolean;
  stockDetail: StockDetail;
}

function InterestStock({ interest, stockDetail }: InterestStockProps) {
  const { name, symbol, price, currency } = stockDetail;

  const classes = useStyles();
  return (
    <div className={classes.root}>
      <div className={classes.info}>
        <div>{name}</div>
        <div>{symbol}</div>
      </div>
      <Typography className={classes.price} variant="subtitle2">
        {price} {currency}
      </Typography>
      <IconButton
        color={'inherit'}
        className={classes.iconBtn}
      >
        {interest ? <Remove /> : <Add />}
      </IconButton>
    </div>
  )
}

interface InterestStockListProps {
  interest: boolean;
  stockDetails: StockDetail[];
}

function InterestStockList({ interest, stockDetails }: InterestStockListProps) {
  return (
    <div>
      {stockDetails.map(stockDetail => (
          <InterestStock key={stockDetail.stockId} interest={interest} stockDetail={stockDetail} />)
        )
      }
    </div>
  )
}

export default InterestStockList;
