export interface BaseResponse<T> {
  success: boolean;
  code: number;
  message: string;
  data: T;
  errors?: object[];
}

export interface PageResponse<T> {
  contents: T[];
  nextCursor: number;
  hasNext: boolean;
}

export interface IUser {
  profileImage: string;
  name: string;
  gender: string;
  th: string;
  campusName: string;
}

export interface IUserAdd {
  mbti: string;
  classNum: number;
  major: string;
  birth: string;
  location: string;
  interest: string;
}
export interface IUserCoin {
  coin: number;
}

export interface IUserFriend {
  friend: number;
}

export interface IUserPick {
  pick: number;
}

export interface IUserAttendance {
  streak: number;
  todayChecked: boolean;
}