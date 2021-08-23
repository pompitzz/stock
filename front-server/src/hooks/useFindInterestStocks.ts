import { useDispatch, useSelector } from 'react-redux';
import { useEffect } from 'react';
import { RootState } from '../modules';
import { findInterestStocksAction } from '../modules/user';
import useIsLoggedIn from './useIsLoggedIn';

export default function useFindInterestStocks() {
  const dispatch = useDispatch();
  const isLoggedIn = useIsLoggedIn();
  const findInterestStocks = () => {
    if (isLoggedIn) {
      dispatch(findInterestStocksAction.request());
    }
  }
  useEffect(() => {
    if (isLoggedIn) {
      dispatch(findInterestStocksAction.request());
    }
  }, [dispatch, isLoggedIn]);
  const { interestStocks, loading, error } = useSelector(({ user }: RootState) => ({
    interestStocks: user.interestStocks.data || [],
    loading: user.interestStocks.loading,
    error: user.interestStocks.error,
  }));
  return {
    interestStocks,
    loading,
    error,
    findInterestStocks,
  }
}
