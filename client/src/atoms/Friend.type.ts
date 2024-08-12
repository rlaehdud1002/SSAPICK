import { off } from "process";

export interface IFriend {
  userId: number;
  name: string;
  profileImage: string;
  cohort: number;
  campusSection: number;
  follow: boolean;
  sameCampus: boolean;
}
export interface ISearchData<T>{
  totalPages: number;
  totalElements: number;
  content: T;
  empty: boolean;
  first: boolean;
  last: boolean;
  number: number;
  numberOfElements: number;
  pageable: {
    offset: number;
    pageNumber: number;
    pageSize: number;
    paged: boolean;
    sort: {
      empty: boolean;
      sorted: boolean;
      unsorted: boolean;
    };}
  size: number;
}

export interface IContent{
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
