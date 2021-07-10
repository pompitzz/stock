import axios, { AxiosInstance } from 'axios';

const axiosInstance = axios.create();
configure(axiosInstance);

export default function getAxios(): AxiosInstance {
  return axiosInstance;
}

function configure(axios: AxiosInstance) {
  configureParameterSerializer(axios);
}

function configureParameterSerializer(axios: AxiosInstance) {
  const concatParam = (prev: string, cur: string) => {
    if (!cur) {
      return prev;
    }
    return `${prev}&${cur}`;
  };
  axios.defaults.paramsSerializer = (params) => {
    return Object.entries(params)
      .map(([key, value]) => {
        if (Array.isArray(value)) {
          if (value.length === 0) {
            return '';
          }
          return value.map(element => `${key}=${element}`).reduce(concatParam);
        }
        return `${key}=${value}`
      }).reduce(concatParam);
  }
}
