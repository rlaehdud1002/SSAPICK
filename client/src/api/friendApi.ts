import instance from 'api/clientApi';
import { IFriend, ISearchFriend } from 'atoms/Friend.type';
import { BaseResponse } from 'atoms/User.type';

// 친구 목록 get
export const getFriendsList = async (): Promise<IFriend[]> => {
  const {
    data: { success, data, message },
  } = await instance.get<BaseResponse<IFriend[]>>('/follow');

  if (!success) {
    throw new Error('친구 목록 조회 실패');
  }

  console.log('getFriendsList');

  return data;
};

// 유저 팔로우
export const postAddFriend = async (userId: number): Promise<void> => {
  const {
    data: { success, data },
  } = await instance.post<BaseResponse<void>>(`/follow/${userId}`);

  if (!success) {
    throw new Error('친구 팔로우 실패');
  }

  console.log('postAddFriend');

  return data;
};

// 유저 언팔로우
export const deleteFriend = async (userId: number): Promise<void> => {
  const response = await instance.delete(`/follow/${userId}`);

  if (response.status !== 204) {
    throw new Error('친구 언팔로우 실패');
  }
};

// 추천 친구 목록 조회
export const getRecommendFriendsList = async (): Promise<IFriend[]> => {
  const {
    data: { success, data, message },
  } = await instance.get<BaseResponse<IFriend[]>>('/follow/recommend');

  if (!success) {
    throw new Error('추천 친구 목록 조회 실패');
  }

  console.log('getRecommendFriendsList');

  return data;
};

// 친구 검색 리스트
export const getSearchFriendsList = async (q: string | undefined): Promise<ISearchFriend> => {
  const {
    data: { success, data },
  } = await instance.get<BaseResponse<ISearchFriend>>('/user/search', { params: { q } });

  if (!success) {
    throw new Error('친구 검색 실패');
  }
  console.log("검색 친구 조회 성공")
  return data;
};