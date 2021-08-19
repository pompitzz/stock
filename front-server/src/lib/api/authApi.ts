import getAxios from './axiosProvider';
import { AxiosResponse } from 'axios';
import { JwtToken, LoginRequest } from '../../types/auth';

const axios = getAxios();

const authApi = {
  loginKakao(loginRequest: LoginRequest): Promise<JwtToken> {
    return axios.get('/login/kakao', { params: loginRequest })
      .then(({ data }: AxiosResponse<JwtToken>) => data);
  },
  validateToken(): Promise<String> {
    return axios.get('/token/validate').then(({ data }: AxiosResponse<String>) => data);
  },
}

export default authApi;
