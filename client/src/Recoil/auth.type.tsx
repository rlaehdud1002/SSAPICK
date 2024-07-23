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
  