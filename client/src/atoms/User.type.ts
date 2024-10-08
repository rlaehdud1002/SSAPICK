import { IHint } from './Hint.type';

export interface ISendUser {
  residentailArea?: string;
  major?: string;
  campusSection: number;
  gender: string;
  interest?: string;
  campusName: string;
  name: string;
  birth?: string;
  mbti?: string;
  cohort: number;
}

export interface IUserInfo {
  id: number;
  username: string;
  name: string;
  profileImage: string;
  gender: string;
  cohort: number;
  campusName: string;
  section: number;
  pickco: number;
  pickCount: number;
  followingCount: number;
  hints: Array<IHint>;
}

export interface IEmpty {
  success: boolean;
  status: number;
  message: string;
  data: null;
}

export interface SuccessResponse<T> {
  success: boolean;
  message: string;
  data: T;
  status?: number;
  errors?: object[];
}

export interface BaseResponse<T> {
  success: boolean;
  code?: number;
  message: string;
  data: T;
  status?: number;
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
export interface IPickco {
  pickco: number;
}

export interface IUserAttendance {
  streak: number;
  todayChecked: boolean;
}
