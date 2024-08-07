import { BaseResponse } from "atoms/User.type";
import instance from "./clientApi";
import { IBlock } from "atoms/Block.type";

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
  export const block = async (userId:number): Promise<void> => {
    const {
      data: { success, data, message },
    } = await instance.post('/auth/sign-out', authToken);
    if (!success) {
      throw new Error(message);
    }
    return data;
  };
