import { INotification } from 'atoms/Notification.type';
import { BaseResponse } from 'atoms/User.type';
import instance from './clientApi';
import { IPaging } from 'atoms/Pick.type';

export const registerToken = async (token: string): Promise<void> => {
  const {
    data: { success, data, message },
  } = await instance.post('/notification/register', {
    token,
  });
  if (!success) {
    throw new Error(message);
  }
  return data;
};

// 알람 리스트 조회
export const getNotificationList = async (
  page: number,
  size: number,
): Promise<IPaging<INotification[]>> => {
  const {
    data: { success, data, message },
  } = await instance.get<BaseResponse<IPaging<INotification[]>>>(
    `/notification?page=${page}&size=${size}`,
  );

  if (!success) {
    throw new Error(message);
  }

  return data;
};

// 알람 읽음 처리
export const readNotification = async (): Promise<void> => {
  const {
    data: { success, message },
  } = await instance.post('/notification');

  if (!success) {
    throw new Error(message);
  }
};
