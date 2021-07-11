import getAxios from './axiosProvider';
import { Page, ServerPageResponse } from '../../types/pages';
import { StockContext } from '../../types/stock';
import { SearchRequest } from '../../types/common';
import { AxiosResponse } from 'axios';
import apiResponseConverter from '../utils/apiResponseConverter';

const axios = getAxios();

const stockApi = {
  search({ query, pageRequest }: SearchRequest): Promise<Page<StockContext>> {
    const params = {
      query,
      ...pageRequest
    };
    return axios.get('/stock/search', { params: params })
      .then(({ data }: AxiosResponse<StockSearchResponse>) => apiResponseConverter.convertPage(data.stockContexts));
  }
}

type StockSearchResponse = {
  stockContexts: ServerPageResponse<StockContext>,
}

export default stockApi;
