import instance from 'api/clientApi';
import { IPaging, IPick, IPickCreate, IPickInfo } from 'atoms/Pick.type';
import { BaseResponse } from 'atoms/User.type';

// 받은 pick 조회
export const getReceivePick = async (
  page: number,
  size: number,
): Promise<IPaging<IPick[]>> => {
  const {
    data: { success, data },
  } = await instance.get<BaseResponse<IPaging<IPick[]>>>(
    `/pick/receive?page=${page}&size=${size}`,
  );

  if (!success) {
    throw new Error('받은 pick 조회 실패');
  }
  return data;
};

// pick 생성
export const postCreatePick = async (
  pickData: IPickCreate,
): Promise<IPickInfo> => {
  const {
    data: { success, data },
  } = await instance.post<BaseResponse<IPickInfo>>('/pick', pickData);

  if (!success) {
    throw new Error('pick 생성 실패');
  }

  return data;
};

// pick 상태 조회
export const getPickInfo = async (): Promise<IPickInfo> => {
  const {
    data: { success, data },
  } = await instance.get<BaseResponse<IPickInfo>>(`/pick`);

  if (!success) {
    throw new Error('pick 조회 실패');
  }

  return data;
};

// 힌트 열기
export const getHint = async (pickId: number): Promise<string> => {
  const {
    data: { success, data },
  } = await instance.get<BaseResponse<string>>(`/hint/${pickId}`);

  if (!success) {
    throw new Error('힌트 조회 실패');
  }

  return data;
};

// pick 알림 설정
export const patchPickAlarm = async (pickId: number): Promise<void> => {
  const {
    data: { success, data },
  } = await instance.patch<BaseResponse<void>>(`/pick/${pickId}`);

  if (!success) {
    throw new Error('pick 알림 설정 실패');
  }

  return data;
};

// 사용자 리롤
export const patchPickUserReRoll = async (): Promise<void> => {
  const {
    data: { success, data },
  } = await instance.patch<BaseResponse<void>>(`/pick/re-roll`);

  if (!success) {
    throw new Error('사용자 리롤 실패');
  }

  return data;
};

export const getAlarmPick = async (): Promise<IPick> => {
  const {
    data: { success, data },
  } = await instance.get<BaseResponse<IPick>>(`/pick/alarm`);

  if (!success) {
    throw new Error('받은 알람 조회');
  }

  return data;
};
