export interface IFriend {
  userId: number;
  name: string;
  profileImage: string;
  cohort: number;
  campusSection: number;
  follow: boolean;
  sameCampus: boolean;
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
      profileImage: string;
    },
  ];
  number?: number;
  sort?: object;
  first?: boolean;
  last?: boolean;
  numberOfElements?: number;
  empty?: boolean;
}
