import instance from "api/clientApi";
import { IAlarm, IAlarmAll } from "atoms/Alarm.type";
import { BaseResponse } from "atoms/User.type";

// 알람 조회
export const getAlarm = async (): Promise<IAlarm> => {
  const response = await instance.get<BaseResponse<IAlarm>>("/alarm");
  const { success, data } = response.data;

  if (!success) {
    throw new Error("알람 조회 실패");
  }

  return data;
};

// 알람 설정
export const postAlarm = async (alarmData: IAlarm): Promise<void> => {
  const {
    data: { success },
  } = await instance.post<BaseResponse<void>>("/alarm", alarmData);

  if (!success) {
    throw new Error("알람 설정 실패");
  }

  console.log("postAlarm");
};

// 전체 알람 설정
export const postAlarmAll = async (updateAll: IAlarmAll): Promise<void> => {
  const {
    data: { success },
  } = await instance.post<BaseResponse<void>>("/alarm/all", updateAll);

  if (!success) {
    throw new Error("전체 알람 설정 실패");
  }

  console.log("postAlarm");
};
