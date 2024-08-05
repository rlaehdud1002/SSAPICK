// export interface IUser {
//   "id": number,
//   "username": string,
//   "name": string,
//   "profileImage": string,
//   "gender": string,
//   "cohort": number,
//   "campusName": string,
//   "section": number,
//   "pickco": number,
//   "pickCount": number,
//   "followingCount": number,
//   "hints": Array<string>,
// }

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