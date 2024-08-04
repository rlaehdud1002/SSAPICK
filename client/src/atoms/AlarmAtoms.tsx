import { atom } from "recoil";
import { IAlarm } from "atoms/Alarm.type";

export const alarmSettingsState = atom<IAlarm | null>({
  key: "alarmSettingsState",
  default: null,
});

// 테스트를 위해 default 설정
// export const alarmSettingsState = atom<IAlarm>({
//   key: "alarmSettingsState",
//   default: {
//     nearbyAlarm: true,
//     messageAlarm: false,
//     addQuestionAlarm: true,
//     pickAlarm: false,
//   },
// });
