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

  hasToken(): boolean {
    return !!this.getToken()
  }

  configureTokenIfHas() {
    if (this.hasToken()) {
      setAuthorizationHeader(this.getToken())
    }
  }

  getToken(): string {
    return localStorage.getItem(KEY)!!;
  }
}

const tokenService = new TokenService();

export default tokenService;
