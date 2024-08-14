import instance from 'api/clientApi';
import { BaseResponse, IUserAttendance } from 'atoms/User.type';

// 출석 조회
export const getAttendance = async (): Promise<IUserAttendance> => {
  const {
    data: { success, data },
  } = await instance.get<BaseResponse<IUserAttendance>>('/attendance');

  if (!success) {
    throw new Error('출석 조회 실패');
  }

  return data;
};

// 출석 체크
export const postAttendance = async (): Promise<IUserAttendance> => {
  const {
    data: { success, data },
  } = await instance.post<BaseResponse<IUserAttendance>>('/attendance');

  if (!success) {
    throw new Error('출석 체크 실패');
  }

  return data;
};
