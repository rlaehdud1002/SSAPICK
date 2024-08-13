export interface IRank {
  questionUserRanking: IUserRanking[];
  topPickReceivers: IRankList[];
  topPickSenders: IRankList[];
  topMessageReceivers: IRankList[];
  topMessageSenders: IRankList[];
  topReservePickcoUsers: IRankList[];
  topSpendPickcoUsers: IRankList[];
}

export interface IRankList {
  user: IRankUser;
  count: number;
}

export interface IRankUser {
  name: string;
  cohort: number;
  campusName: string;
  section: number;
  profileImage: string;
}

export interface IUserRanking {
  campusName: string;
  cohort: number;
  count: number;
  name: string;
  profileImage: string;
  questionContent: string;
  questionId: number;
  section: number;
  userId: number;
}
