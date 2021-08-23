import React, { useState } from 'react';
import { makeStyles, Theme } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import MuiDialogTitle from '@material-ui/core/DialogTitle';
import MuiDialogActions from '@material-ui/core/DialogActions';
import Typography from '@material-ui/core/Typography';
import StockSearchInput from '../../components/StockSearchInput';
import { CircularProgress, Divider } from '@material-ui/core';
import useSearchStock from '../../hooks/useSearchStock';
import InterestStockSearchList from './InterestStockSearchList';
import InterestStockList from './InterestStockList';
import { StockDetail } from '../../types/stock';
import useInterestStockManager from '../../hooks/useInterestStockManager';
import useDialog from '../../hooks/useDialog';

const useStyles = makeStyles((theme: Theme) => ({
    title: {
      padding: theme.spacing(1.5)
    },
    search: {
      padding: theme.spacing(1),
      maxWidth: 450
    },
    searchResult: {
      maxHeight: '300px',
      overflow: 'scroll',
    },
    interestTitle: {
      fontWeight: 'bold',
      paddingLeft: theme.spacing(2),
      paddingRight: theme.spacing(2),
      paddingTop: theme.spacing(1)
    },
    interestStocks: {
      maxHeight: '250px',
      overflow: 'scroll',
    }
  })
);

interface InterestStockEditorProps {
  parentInterestStocks: StockDetail[];
  findInterestStocks: () => void;
}

export default function InterestStockEditor({ parentInterestStocks, findInterestStocks }: InterestStockEditorProps) {
  const { interestStocks, interestStockIds, addInterestStock, removeInterestStock, saveInterestStocks } = useInterestStockManager(parentInterestStocks);
  const { searchStock, loading, stockPage, error } = useSearchStock(false);
  const { open, openDialog, closeDialog } = useDialog();

  const [submitLoading, setSubmitLoading] = useState(false);
  const onSubmit = () => {
    setSubmitLoading(true);
    saveInterestStocks()
      .then(() => {
        findInterestStocks();
        closeDialog();
      })
      .catch((e) => {
        // TODO: 스낵바 추가하기
        console.log('failed save interest stocks', e);
      })
      .finally(() => setSubmitLoading(false));
  }
  const classes = useStyles();
  return (
    <div>
      <Button variant="contained" onClick={openDialog}>
        관심 종목 관리
      </Button>
      <Dialog open={open}>
        <MuiDialogTitle disableTypography className={classes.title}>
          <Typography variant="h6">관심 종목 관리</Typography>
        </MuiDialogTitle>
        <Divider />
        <div className={classes.search}>
          <StockSearchInput searchStock={searchStock} fullWidth />
        </div>
        <div className={classes.searchResult}>
          <InterestStockSearchList stockPage={stockPage} loading={loading} error={error} addInterestStock={addInterestStock} interestStockIds={interestStockIds} />
        </div>
        <Divider />
        <Typography variant="h6" className={classes.interestTitle}>관심 종목</Typography>
        <div className={classes.interestStocks}>
          <InterestStockList stockDetails={interestStocks} interest={true} action={removeInterestStock} />
        </div>
        <Divider />
        <MuiDialogActions>
          <Button variant="outlined" onClick={closeDialog}>
            취소
          </Button>
          <div>
            <Button variant="outlined" onClick={onSubmit}>
              {submitLoading ? <CircularProgress size={24} /> : '저장'}
            </Button>
          </div>
        </MuiDialogActions>
      </Dialog>
    </div>
  );
}
