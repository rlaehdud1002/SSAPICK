import { atom } from "recoil";
import { IAlarm } from "atoms/Alarm.type";

export const alarmSettingsState = atom<IAlarm | null>({
  key: "alarmSettingsState",
  default: null,
});
