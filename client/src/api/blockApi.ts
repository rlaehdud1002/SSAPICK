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
  const response = await instance.delete(`/user-ban/${userId}`);

  if (response.status !== 204) {
    throw new Error('유저 차단 해제 실패');
  }
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
  const response = await instance.delete(`/questions/${questionId}/ban`);

  if (response.status !== 204) {
    throw new Error('질문 차단 해제 실패');
  }
};

// 유저 차단 (쪽지)
export const blockUser = async (userId: number): Promise<void> => {
  const {
    data: { success, data, message },
  } = await instance.post(`/user-ban/${userId}`);

  console.log('쪽지 신고', success, data, message)

  if (!success) {
    throw new Error(message);
  }
  return data;
};