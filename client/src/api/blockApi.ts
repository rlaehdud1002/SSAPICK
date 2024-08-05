import { BaseResponse } from "atoms/User.type";
import instance from "./clientApi";
import { IBlock } from "atoms/Block.type";

// 차단 친구 목록 조회
export const getBlockList = async (): Promise<IBlock[]> => {
  const {
    data: { success, data, message },
  } = await instance.get<BaseResponse<IBlock[]>>('/user-ban');

  if (!success) {
    console.log(message);
    throw new Error('차단 친구 목록 조회 실패');
  }

  return data;
};