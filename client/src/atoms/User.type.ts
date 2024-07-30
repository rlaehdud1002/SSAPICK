export interface JwtToken {
  accessToken: string;
}

export interface BaseResponse<T> {
  isSuccess: boolean;
  code: number;
  message: string;
  data: T;
  errors: object[];
}

export interface PageResponse<T> {
  contents: T[];
  nextCursor: number;
  hasNext: boolean;
}

export interface User {
  image: string;
  name: string;
  gender: string;
  th: number;
  campus: string;
  mbti: string;
  classNum: number;
  major: string;
  birth: string;
  town: string;
  hobby: string;
}
