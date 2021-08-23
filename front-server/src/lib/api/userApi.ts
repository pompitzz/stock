import getAxios from './axiosProvider';
import { StockDetail } from '../../types/stock';
import { AxiosResponse } from 'axios';

const axios = getAxios();

const userApi = {
  findInterestStocks(): Promise<StockDetail[]> {
    return axios.get(`/user/interest-stock`,)
      .then(({ data }: AxiosResponse<FindInterestStocksResponse>) => data.stockDetails);
  }
}

type FindInterestStocksResponse = {
  stockDetails: StockDetail[],
}
export default userApi;
