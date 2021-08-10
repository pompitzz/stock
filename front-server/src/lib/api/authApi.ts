import getAxios from './axiosProvider';
import { ServerPageResponse } from '../../types/pages';
import { StockContext } from '../../types/stock';
import { AxiosResponse } from 'axios';
import { JwtToken, LoginRequest } from '../../types/auth';

const axios = getAxios();

const authApi = {
  loginKakao(loginRequest: LoginRequest): Promise<JwtToken> {
    return axios.get('/login/kakao', { params: loginRequest })
      .then(({ data }: AxiosResponse<JwtToken>) => data);
  },
}

type StockSearchResponse = {
  stockContexts: ServerPageResponse<StockContext>,
}

type StockContextResponse = {
  stockContext: StockContext,
}

export default authApi;
