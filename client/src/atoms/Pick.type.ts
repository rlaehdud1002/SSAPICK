export interface IQuestion {
  id: number;
  banCount?: number;
  skipCount?: number;
  content: string;
  category: ICategory;
  createdAt?: string;
}
// 내가 생성한 질문
export interface IMadeQuestion {
  id: number;
  category: ICategory;
  content: string;
  deletable: boolean;
}

export interface ICategory {
  id: number;
  name: string;
  thumbnail?: string;
}

export interface IPick {
  id: number;
  sender: IPickUser;
  receiver: IPickUser;
  question: IQuestion;
  createdAt: string;
  messageSend: boolean;
  alarm: boolean;
  openedHints: Array<string>;
}

export interface IPaging<T> {
  totalPages: number;
  totalElements: number;
  pageable: {
    pageNumber: number;
    pageSize: number;
    sort: {
      empty: boolean;
      unsorted: boolean;
      sorted: boolean;
    };
    offset: number;
    paged: boolean;
    unpaged: boolean;
  };
  size: number;
  content: T;
  numner: number;
  sort: {
    empty: boolean;
    unsorted: boolean;
    sorted: boolean;
  };
  numberOfElements: number;
  first: boolean;
  last: boolean;
  empty: boolean;
}

export interface IPickUser {
  userId: number;
  nickname?: string;
  gender: string;
  campusName: string;
  campusSection: number;
  campusDescription: string;
  profileImage?: string;
}

export interface IPickCreate {
  receiverId?: number;
  questionId: number;
  status: string;
}

export interface ICreateQuestion {
  categoryId: number;
  content: string;
}

export interface IPickInfo {
  index: number;
  pickCount: number;
  blockCount: number;
  passCount: number;
  cooltime: boolean;
  endTime: string;
}
