import { BaseResponse } from "atoms/User.type";
import instance from "./clientApi"
import { ILocation, ISelectFriend } from "atoms/Location.type";

export const findFriends = async (): Promise<ILocation> => {
    const { data: {data, success, message} } = await instance.get<BaseResponse<ILocation>>("/location");
    return data;
}
// 내 주변 친구 선택 
export const selectFriends = async (selectData: ISelectFriend): Promise<void> => {
    const {
      data: { success, data, message, status },
    } = await instance.post('/location', selectData);
    console.log(status);
    if (!success) {
      console.log(message);
      throw new Error('유저 클릭 실패');
    }
    return data;
  };