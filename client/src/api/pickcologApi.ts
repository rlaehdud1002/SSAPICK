import { IPickcolog } from "atoms/Pickcolog.type";
import instance from "./clientApi";
import { IPaging } from "atoms/Pick.type";
import { BaseResponse } from "atoms/User.type";

export const getPickcolog = async (page: number, size: number): Promise<IPaging<IPickcolog[]>> => {
  const {
    data: { success, data },
  } = await instance.get<BaseResponse<IPaging<IPickcolog[]>>>(
    `user/pickco-log?page=${page}&size=${size}`
  );

  if (!success) {
    throw new Error("픽코로그 조회 실패");
  }

  return data;
};
