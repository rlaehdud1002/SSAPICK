import { atom } from "recoil";
import { IAlarm } from "atoms/Alarm.type";
import { persistAtom } from "./RecoilPersist";

export const alarmSettingsState = atom<IAlarm | null>({
  key: "alarmSettingsState",
  default: null,
});

export const newAlarmState = atom<boolean>({
  key: "newAlarmState",
  default: false,
  effects_UNSTABLE: [persistAtom]
});

export const isMessageModalOpenState = atom<boolean>({
  key: "isMessageModalOpenState",
  default: false,
});