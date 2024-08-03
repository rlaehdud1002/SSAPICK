import instance from "api/clientApi";
import { IAlarm } from "atoms/Alarm.type";
import { BaseResponse } from "atoms/User.type";

// 알람 조회
export const getAlarm = async (): Promise<IAlarm> => {
  const {
    data: { success, data },
  } = await instance.get<BaseResponse<IAlarm>>("/alarm");

  if (!success) {
    throw new Error("알람 조회 실패");
  }

  console.log("getAlarm");

  return data;
};

// 알람 설정
export const postAlarm = async (alarmData: IAlarm): Promise<void> => {
  const {
    data: { success },
  } = await instance.post<BaseResponse<null>>("/alarm", alarmData);

  if (!success) {
    throw new Error("알람 설정 실패");
  }

  console.log("postAlarm");
};
