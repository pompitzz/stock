import { makeStyles, Theme } from '@material-ui/core/styles';
import React, { ChangeEvent } from 'react';
import { IconButton, InputBase, Paper } from '@material-ui/core';
import { Search } from '@material-ui/icons';

const useStyles = makeStyles((theme: Theme) => ({
    root: {
      padding: '2px 4px',
      display: 'flex',
      alignItems: 'center',
      width: (props: SearchBoxProps) => props.fullWidth ? '100%' : 400,
      marginLeft: 'auto',
      marginRight: 'auto',
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


interface SearchBoxProps {
  searchStock: (e: ChangeEvent<HTMLInputElement>) => void;
  fullWidth?: boolean;
}

function StockSearchInput({ searchStock, fullWidth }: SearchBoxProps) {
  const classes = useStyles({ searchStock, fullWidth });
  return (
    <Paper className={classes.root}>
      <InputBase
        className={classes.input}
        placeholder="Search Stocks"
        inputProps={{ 'aria-label': 'search stocks' }}
        onChange={searchStock}
      />
      <IconButton className={classes.iconButton} aria-label="search">
        <Search />
      </IconButton>
    </Paper>
  )
}

export default StockSearchInput;
