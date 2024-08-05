export interface IBlock {
  userId: number;
  nickname: string;
  gender: "M" | "F";
  campusName: string;
  campusSection: number;
  campusDescription: string;
  profileImage: string;
  cohort: number;
}