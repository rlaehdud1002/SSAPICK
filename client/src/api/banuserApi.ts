import instance from "./clientApi";
import { IAuth } from "atoms/Auth.type";
import { BaseResponse, IUserInfo } from "atoms/User.type";

// 유저 차단 post
export const blockUser = async (messageId: number): Promise<void> => {
  const {
    data: { success },
  } = await instance.post<BaseResponse<null>>(`user-ban/${messageId}`);

  if (!success) {
    throw new Error("유저 차단 실패");
  }
};
