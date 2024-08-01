import instance from 'api/clientApi';
import { IPick, IPickCreate } from 'atoms/Pick.type';
import { BaseResponse } from 'atoms/User.type';

// 받은 pick 조회
export const getReceivePick = async (): Promise<IPick[]> => {
  const {
    data: { success, data, message },
  } = await instance.get<BaseResponse<IPick[]>>('/pick/receive');
  if (!success) {
    throw new Error(message);
  }
  return data;
};

// pick 생성
export const postCreatePick = async (pickData: IPickCreate): Promise<void> => {
  await instance.post('/pick', pickData);
};
