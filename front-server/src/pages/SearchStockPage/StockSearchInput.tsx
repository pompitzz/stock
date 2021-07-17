import { makeStyles, Theme } from '@material-ui/core/styles';
import React, { ChangeEvent } from 'react';
import { IconButton, InputBase, Paper } from '@material-ui/core';
import { Search } from '@material-ui/icons';

const useStyles = makeStyles((theme: Theme) => ({
    root: {
      padding: '2px 4px',
      display: 'flex',
      alignItems: 'center',
      width: 400,
      marginLeft: 'auto',
      marginRight: 'auto',
      marginTop: theme.spacing(3),
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
  searchStock: (e: ChangeEvent<HTMLInputElement>) => void
}

function StockSearchInput({ searchStock }: SearchBoxProps) {
  const classes = useStyles();
  return (
    <Paper component="form" className={classes.root}>
      <InputBase
        className={classes.input}
        placeholder="Search Stocks"
        inputProps={{ 'aria-label': 'search stocks' }}
        onChange={searchStock}
      />
      <IconButton type="submit" className={classes.iconButton} aria-label="search">
        <Search />
      </IconButton>
    </Paper>
  )
}

export default StockSearchInput;
