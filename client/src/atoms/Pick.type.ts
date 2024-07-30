export interface Question {
  id: number;
  banCount?: number;
  skipCount?: number;
  content: string;
  category: Category;
  createdAt: string;
}

export interface Category {
  id: number;
  name: string;
  thumbnail?: string;
}

export interface Pick {
  id: number;
  sender: PickUser;
  receiver: PickUser;
  createdAt: string;
  messageSend: boolean;
  question: Question;
}

export interface PickUser {
  userId?: number;
  nickname?: string;
  gender: string;
  campusName: string;
  campusSection: number;
  campusDescription: string;
  profileImage?: string;
}
