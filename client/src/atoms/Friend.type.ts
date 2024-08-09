export interface IFriend {
  userId: number;
  nickname: string;
  gender: string;
  campusName: string;
  campusSection: number;
  campusDescription: string;
  profileImage: string;
}

export interface ISearchFriend {
  totalPages?: number;
  totalElements?: number;
  pageables?: object;
  size?: number;
  content: [
    {
      name: string;
      cohort: number;
      campusSection: number;

    }
  ];
  number?: number;
  sort?: object;
  first?: boolean;
  last?: boolean;
  numberOfElements?: number;
  empty?: boolean;
}

