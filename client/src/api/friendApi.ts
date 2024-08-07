import instance from 'api/clientApi';
import { IFriend } from 'atoms/Friend.type';
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

// 친구 추가
export const postAddFriend = async (userId: number): Promise<void> => {
  const {
    data: { success, data, message },
  } = await instance.post<BaseResponse<void>>(`/follow/${userId}`);

  if (!success) {
    throw new Error('친구 추가 실패');
  }

  console.log('postAddFriend');

  return data;
};

// 친구 삭제
export const deleteFriend = async (userId: number): Promise<void> => {
  const {
    data: { success, data, message },
  } = await instance.delete<BaseResponse<void>>(`/follow/${userId}`);

  if (!success) {
    throw new Error('친구 삭제 실패');
  }

  console.log('deleteFriend');

  return data;
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