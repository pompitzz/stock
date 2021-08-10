import { removeAuthorizationHeader, setAuthorizationHeader } from '../api/axiosProvider';

const KEY = 'token';

const tokenService = {
  saveToken(token: string) {
    localStorage.setItem(KEY, token);
    setAuthorizationHeader(token);
  },
  removeToken() {
    localStorage.removeItem(KEY);
    removeAuthorizationHeader();
  },
  hasToken() {
    return !!localStorage.getItem(KEY)
  }
}

export default tokenService;
