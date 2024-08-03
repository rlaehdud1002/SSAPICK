import instance from 'api/clientApi';
import { IRank } from 'atoms/Rank.type';
import { BaseResponse } from 'atoms/User.type';

// 랭킹 조회
export const getRankList = async (): Promise<IRank> => {
  const {
    data: { data, success },
  } = await instance.get<BaseResponse<IRank>>('/ranking/all');

  if (!success) {
    throw new Error('랭킹 조회 실패');
  }

  console.log('getRankList');

  return data;
};
