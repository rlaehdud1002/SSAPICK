import { IBlock, IBlockQuestion } from "atoms/Block.type";
import { BaseResponse } from "atoms/User.type";
import instance from "./clientApi";

// 차단 유저 목록 조회
export const getBlockedList = async (): Promise<IBlock[]> => {
  const {
    data: { success, data },
  } = await instance.get<BaseResponse<IBlock[]>>('/user-ban');

  if (!success) {
    throw new Error('차단 유저 목록 조회 실패');
  }

  console.log('getBlockedList');

  return data;
};

// 유저 차단 해제
export const blockCancel = async (userId: number): Promise<void> => {
  const {
    data: { success, data, message },
  } = await instance.post(`/user-ban/${userId}`);

  if (!success) {
    throw new Error(message);
  }
  return data;
};

// 차단 질문 목록 조회
export const getBlockedQuestionList = async (): Promise<IBlockQuestion[]> => {
  const {
    data: { success, data },
  } = await instance.get<BaseResponse<IBlockQuestion[]>>('/questions/bans');

  if (!success) {
    throw new Error('차단 질문 목록 조회 실패');
  }
  return data;
}

// 질문 차단 해제
export const blockQuestionCancel = async (questionId: number): Promise<void> => {
  const {
    data: { success, data, message },
} = await instance.post(`/questions/${questionId}/ban`);

  if (!success) {
    throw new Error(message);
  }
  return data;
};