import { removeAuthorizationHeader, setAuthorizationHeader } from '../api/axiosProvider';

const KEY = 'token';

class TokenService {
  saveToken(token: string) {
    localStorage.setItem(KEY, token);
    setAuthorizationHeader(token);
  }

  removeToken() {
    localStorage.removeItem(KEY);
    removeAuthorizationHeader();
  }

  hasToken() {
    return !!TokenService.getToken()
  }

  configureTokenIfHas() {
    if (this.hasToken()) {
      setAuthorizationHeader(TokenService.getToken())
    }
  }

  private static getToken(): string {
    return localStorage.getItem(KEY)!!;
  }
}

const tokenService = new TokenService();

export default tokenService;
