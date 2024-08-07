export interface IAuth {
  loginId: string;
  password: string;
}

export interface JwtToken {
  accessToken: string;
  refreshToken: string;
}