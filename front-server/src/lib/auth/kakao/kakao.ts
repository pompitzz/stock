// @ts-ignore
import * as Kakao from './sdk.js';

export function kakaoLogin() {
  if (!Kakao.isInitialized()) {
    Kakao.init('c0c030584144e9ee312c088b1eb146e9');
  }
  Kakao.Auth.authorize({
    redirectUri: KAKAO.REDIRECT_URL,
  });
}

export const KAKAO = {
  REDIRECT_URL: `${window.location.protocol}//${window.location.host}/login`,
};
