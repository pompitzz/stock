export type LoginRequest = {
  code: string;
  redirectUrl: string;
}

export type JwtToken = {
  token: string;
}
