export interface QuestionAtom {
  id: number;
  banCount?: number;
  skipCount?: number;
  content: string;
  category: CategoryAtom;
  createdAt: string;
}

export interface CategoryAtom {
  id: number;
  name: string;
  thumbnail?: string;
}

export interface PickAtom {
  id: number;
  sender: PickUserAtom;
  receiver: PickUserAtom;
  createdAt: string;
  messageSend: boolean;
  question: QuestionAtom;
}

export interface PickUserAtom {
  userId?: number;
  nickname?: string;
  gender: string;
  campusName: string;
  campusSection: number;
  campusDescription: string;
  profileImage?: string;
}
