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

interface InterestStockListProps {
  interest: boolean;
  stockDetails: StockDetail[];
  action: (interestStock: StockDetail) => void;
}

function InterestStockList({ interest, stockDetails, action }: InterestStockListProps) {
  const classes = useStyles();
  return (
    <div>
      {
        stockDetails.map(stockDetail => {
          const { name, symbol, price, currency, stockId } = stockDetail;
          return (
            <div className={classes.root} key={stockId}>
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
                onClick={() => action(stockDetail)}
              >
                {interest ? <Remove /> : <Add />}
              </IconButton>
            </div>
          )
        })
      }

    </div>
  )
}

export default InterestStockList;
