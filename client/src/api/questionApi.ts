import instance from 'api/clientApi';
import {
  ICategory,
  ICreateQuestion,
  IMadeQuestion,
  IQuestion,
} from 'atoms/Pick.type';
import { BaseResponse } from 'atoms/User.type';

// 로그인 된 사용자에게 맞는 질문 조회
export const getQuestion = async (): Promise<IQuestion[]> => {
  const {
    data: { success, data },
  } = await instance.get<BaseResponse<IQuestion[]>>('/questions/pick');

  if (!success) {
    throw new Error('질문 조회 실패');
  }

  return data;
};

// 질문 생성
export const postCreateQuestion = async (
  questionData: ICreateQuestion,
): Promise<void> => {
  const {
    data: { success },
  } = await instance.post<BaseResponse<null>>('/questions', questionData);

  if (!success) {
    throw new Error('질문 생성 실패');
  }
};

// 내가 받은 질문 TOP3 조회
export const getMyQuestionRank = async (): Promise<IQuestion[]> => {
  const {
    data: { success, data },
  } = await instance.get<BaseResponse<IQuestion[]>>('/questions/rank');

  if (!success) {
    throw new Error('받은 질문 조회 실패');
  }

  return data;
};

// 유저 아이디로 질문 조회
export const getQuestionByUser = async (): Promise<IMadeQuestion[]> => {
  const {
    data: { success, data },
  } = await instance.get<BaseResponse<IMadeQuestion[]>>(`/questions/me`);

  if (!success) {
    throw new Error('질문 조회 실패');
  }

  return data;
};

// 전체 카테고리 조회
export const getCategory = async (): Promise<ICategory[]> => {
  const {
    data: { success, data },
  } = await instance.get<BaseResponse<ICategory[]>>('/questions/categories');

  if (!success) {
    throw new Error('카테고리 조회 실패');
  }

  return data;
};

// 생성한 질문 삭제
export const deleteQuetion = async (questionId: number): Promise<void> => {
  const {
    data: { success, message },
  } = await instance.delete<BaseResponse<null>>(`/questions/${questionId}`);

  if (!success) {
    throw new Error(message);
  }
};
