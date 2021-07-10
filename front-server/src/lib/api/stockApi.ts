import getAxios from './axiosProvider';
import { Page, ServerPageResponse } from '../../types/pages';
import { StockSummary } from '../../types/stock';
import { SearchRequest } from '../../types/common';
import { AxiosResponse } from 'axios';
import apiResponseConverter from '../utils/apiResponseConverter';

const axios = getAxios();

const stockApi = {
  search({ query, pageInfo }: SearchRequest): Promise<Page<StockSummary>> {
    const params = {
      query,
      ...pageInfo.toPageRequest()
    };
    return axios.get('/stock/search', { params: params })
      .then(({ data }: AxiosResponse<StockSearchResponse>) => apiResponseConverter.convertPage(data.stockSummaries));
  }
}

type StockSearchResponse = {
  stockSummaries: ServerPageResponse<StockSummary>,
}

export default stockApi;
