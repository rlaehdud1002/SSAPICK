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
  profileImage: string;
  name: string;
  gender: string;
  th: string;
  campusName: string;

}

export interface UserAdd {
  mbti: string;
  classNum: number;
  major: string;
  birth: string;
  location: string;
  interest: string;
}
export interface UserCoin {
  coin: number;
}

export interface UserFriend {
  friend: number;
}

export interface UserPick {
  pick: number;
}
