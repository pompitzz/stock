import React, { ChangeEvent, useState } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import { HistoricalPrice } from '../types/stock';
import Typography from '@material-ui/core/Typography';
import Toolbar from '@material-ui/core/Toolbar';
import TablePagination from '@material-ui/core/TablePagination';
import Switch from '@material-ui/core/Switch';
import FormControlLabel from '@material-ui/core/FormControlLabel';

const useStyles = makeStyles({
  table: {
    minWidth: 650,
  },
});

interface HistoricalPricesTableProps {
  historicalPrices: HistoricalPrice[];
}

function getDateNumber(historicalPrice: HistoricalPrice) {
  return parseInt(historicalPrice.date.replaceAll('-', ''), 10)
}

export default function HistoricalPricesTable({ historicalPrices }: HistoricalPricesTableProps) {
  const classes = useStyles();
  const [page, setPage] = useState(0);
  const [dense, setDense] = useState(true);
  const [rowsPerPage, setRowsPerPage] = useState(20);

  const handleChangePage = (event: unknown, newPage: number) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event: ChangeEvent<HTMLInputElement>) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  const handleChangeDense = (event: ChangeEvent<HTMLInputElement>) => {
    setDense(event.target.checked);
  };

  return (
    <div>
      <Paper>
        <Toolbar>
          <Typography variant="h6" id="tableTitle" component="div">
            Historical Price
          </Typography>
          <FormControlLabel
            control={<Switch checked={dense} onChange={handleChangeDense} />}
            label="Dense padding"
            style={{ marginLeft: 'auto' }}
          />
        </Toolbar>
        <TableContainer component={Paper}>
          <Table
            className={classes.table}
            size={dense ? 'small' : 'medium'}
          >
            <TableHead>
              <TableRow>
                <TableCell>Date</TableCell>
                <TableCell align="right">Open</TableCell>
                <TableCell align="right">High</TableCell>
                <TableCell align="right">Low</TableCell>
                <TableCell align="right">Close</TableCell>
                <TableCell align="right">Volume</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {historicalPrices
                .sort((price1, price2) => getDateNumber(price2) - getDateNumber(price1))
                .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                .map((price, index) => (
                  <TableRow key={index}>
                    <TableCell>{price.date}</TableCell>
                    <TableCell align="right">{price.open}</TableCell>
                    <TableCell align="right">{price.high}</TableCell>
                    <TableCell align="right">{price.low}</TableCell>
                    <TableCell align="right">{price.close}</TableCell>
                    <TableCell align="right">{price.volume}</TableCell>
                  </TableRow>
                ))}
            </TableBody>
          </Table>
        </TableContainer>
        <TablePagination
          rowsPerPageOptions={[10, 20, 30, 50]}
          component="div"
          count={historicalPrices.length}
          rowsPerPage={rowsPerPage}
          page={page}
          onPageChange={handleChangePage}
          onRowsPerPageChange={handleChangeRowsPerPage}
        />
      </Paper>
    </div>
  );
};
