import instance from 'api/clientApi';
import { IUserAttendance } from 'atoms/User.type';

// 출석 조회
export const getAttendance = async (): Promise<IUserAttendance> => {
  const {
    data: { success, message, data },
  } = await instance.get('/attendance');

  if (!success) {
    throw new Error(message);
  }

  return data;
};

// 출석 체크
export const postAttendance = async (): Promise<void> => {
  await instance.post('/attendance');
};
