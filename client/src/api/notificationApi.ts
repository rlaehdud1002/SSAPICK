import { INotification } from "atoms/Notification.type";
import { BaseResponse } from "atoms/User.type";
import instance from "./clientApi";

export const registerToken = async (token: string): Promise<void> => {
  const {
    data: { success, data, message },
  } = await instance.post("/notification/register", {
    token,
  });
  if (!success) {
    throw new Error(message);
  }
  return data;
};

// 알람 리스트 조회
export const getNotificationList = async (): Promise<INotification[]> => {
  const {
    data: { success, data, message },
  } = await instance.get("/notification/list");
  if (!success) {
    throw new Error(message);
  }
  console.log("data : ", data);
  return data;
};
