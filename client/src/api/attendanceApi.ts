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

  console.log('getAttendance');

  return data;
};

// 출석 체크
export const postAttendance = async (): Promise<void> => {
  const {
    data: { success, message },
  } = await instance.post<BaseResponse<null>>('/attendance');

  if (!success) {
    throw new Error(message);
  }

  console.log('postAttendance');
};
