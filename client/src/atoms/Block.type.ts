
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

export interface IBlockQuestion {
  id: number,
  banCount: number,
  skipCount: number,
  category: {
    id: number,
    name: string,
    thumbnail: string,
  },
  content: string,
}