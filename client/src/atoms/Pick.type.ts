export interface IQuestion {
  id: number;
  banCount?: number;
  skipCount?: number;
  content: string;
  category: ICategory;
  createdAt?: string;
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
  createdAt: string;
  messageSend: boolean;
  question: IQuestion;
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
  receiverId: number;
  questionId: number;
  index: number;
  status: string;
}

export interface ICreateQuestion {
  categoryId: number;
  content: string;
}
