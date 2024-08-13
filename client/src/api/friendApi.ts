import instance from "api/clientApi";
import { IContent, IFriend, ISearchData, ISearchFriend } from "atoms/Friend.type";
import { IPaging } from "atoms/Pick.type";
import { BaseResponse } from "atoms/User.type";

// 친구 목록 get
export const getFriendsList = async (): Promise<IFriend[]> => {
  const {
    data: { success, data, message },
  } = await instance.get<BaseResponse<IFriend[]>>("/follow");

  if (!success) {
    throw new Error("친구 목록 조회 실패");
  }

  return data;
};

// 유저 팔로우
export const postAddFriend = async (userId: number): Promise<void> => {
  const {
    data: { success, data },
  } = await instance.post<BaseResponse<void>>(`/follow/${userId}`);

  if (!success) {
    throw new Error("친구 팔로우 실패");
  }

  console.log("친구 팔로우 성공");

  return data;
};

// 유저 언팔로우
export const deleteFriend = async (userId: number): Promise<void> => {
  const response = await instance.delete(`/follow/${userId}`);

  if (response.status !== 204) {
    throw new Error("친구 언팔로우 실패");
  }
};

// 추천 친구 목록 조회
export const getRecommendFriendsList = async (
  page: number,
  size: number
): Promise<IPaging<IContent[]>> => {
  const {
    data: { success, data, message },
  } = await instance.get<BaseResponse<IPaging<IContent[]>>>(
    `/follow/recommend?page=${page}&size=${size}`
  );

  if (!success) {
    throw new Error("추천 친구 목록 조회 실패");
  }

  console.log("추천 친구 목록 조회 성공");

  return data;
};

// 친구 검색 리스트
export const getSearchFriendsList = async (
  page: number,
  size: number,
  q: string
): Promise<IPaging<IContent[]>> => {
  const {
    data: { success, data, message },
  } = await instance.get<BaseResponse<IPaging<IContent[]>>>(
    `/user/search?page=${page}&size=${size}&q=${q}`
  );

  if (!success) {
    throw new Error("친구 검색 실패");
  }
  console.log("검색 친구 조회 성공");
  return data;
};
