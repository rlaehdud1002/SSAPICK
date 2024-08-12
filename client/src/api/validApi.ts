import { IValid } from "atoms/Valid";
import instance from "./clientApi";
import { BaseResponse } from "atoms/User.type";

export const validCheck = async () => {
  try {
    const response = await instance.get<BaseResponse<IValid>>("user/valid");
    const { success, data } = response.data;
  
    if (!success) {
      throw new Error("매터모스트 인증 확인 실패");
    }
  
    return data;
  } catch (error) {
    console.log("유효성 검사 실패", error);
    throw new Error("유효성 검사 실패");
  }
};
