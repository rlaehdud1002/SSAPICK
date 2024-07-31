import { IFriend } from "atoms/friend.type";
import instance from "./client";


// 친구 목록 get
export const getFriends = async () => {
  const response = await instance.get<IFriend[]>("/follow");
  return response.data;
}