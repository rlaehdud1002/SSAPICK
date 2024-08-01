import instance from "api/clientApi";
import { IFriend } from "atoms/Friend.type";


// 친구 목록 get
export const getFriends = async () => {
  const response = await instance.get<IFriend[]>("/follow");
  return response.data;
}