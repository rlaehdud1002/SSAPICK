export interface IRank {
  topPickReceivers: IRankList[];
  topPickSenders: IRankList[];
  topMessageReceivers: IRankList[];
  topMessageSenders: IRankList[];
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
