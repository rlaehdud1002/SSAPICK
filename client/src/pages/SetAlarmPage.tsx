import BackIcon from "icons/BackIcon";
import SetAlarmIcon from "icons/SetAlarmIcon";
import LocationAlarmIcon from "icons/LocationAlarmIcon";
import MessageAlarmIcon from "icons/MessageAlarmIcon";
import QuestionAlarmIcon from "icons/QuestionAlarmIcon";
import PickAlarmIcon from "icons/PickAlarmIcon";

import SetAlarmContent from "components/SetAlarmPage/SetAlarmContent";

import { Switch } from "components/ui/switch";
import { useNavigate } from "react-router-dom";
import { IAlarm, IAlarmAll } from "atoms/Alarm.type";
import { alarmSettingsState } from "atoms/AlarmAtoms";
import { postAlarm, postAlarmAll } from "api/alarmApi";
import { useRecoilState } from "recoil";
import { useEffect, useState } from "react";

const SetAlarm = () => {
  const [alarmSettings, setAlarmSettings] = useRecoilState(alarmSettingsState);
  const [allAlarm, setAllAlarm] = useState(false);
  const nav = useNavigate();

  const handleSwitchChange = async (key: keyof IAlarm, value: boolean) => {
    if (alarmSettings) {
      const updatedSettings = { ...alarmSettings, [key]: value };
      try {
        await postAlarm(updatedSettings);
        setAlarmSettings(updatedSettings);
      } catch (error) {
        console.error("알람 업데이트 실패", error);
      }
    }
  };

  const handleSwitchAllChange = async (value: IAlarmAll) => {
    if (alarmSettings) {
      try {
        console.log(value);
        await postAlarmAll(value);
        setAlarmSettings({
          nearbyAlarm: value.updateAll,
          messageAlarm: value.updateAll,
          addQuestionAlarm: value.updateAll,
          pickAlarm: value.updateAll,
        });
      } catch (error) {
        console.error("전체 알람 업데이트 실패", error);
      }
    }
  };

  useEffect(() => {
    if (alarmSettings) {
      if (
        alarmSettings.nearbyAlarm &&
        alarmSettings.messageAlarm &&
        alarmSettings.addQuestionAlarm &&
        alarmSettings.pickAlarm
      ) {
        setAllAlarm(true);
      } else {
        setAllAlarm(false);
      }
    }
  }, [alarmSettings]);

  return (
    <div className="flex flex-col">
      <div className="flex flex-row items-center m-2 cursor-pointer" onClick={() => nav(-1)}>
        <BackIcon />
        <SetAlarmIcon width={25} height={25} alarmset className="me-2 mt-1" />
        <h1>알림 관리</h1>
      </div>
      <div className="bg-white/50 rounded-lg my-6 mx-8 py-1.5 ps-5 pe-4 flex flex-row items-center justify-between">
        <span className="mx-2">전체 알림</span>
        <Switch
          checked={allAlarm}
          onCheckedChange={(value) => handleSwitchAllChange({ updateAll: value })}
        />
      </div>
      <div className="mx-8">
        <SetAlarmContent
          title="내 주위 알림"
          content="내가 선택한 사람이 반경 10M 이내로 다가오면 알려줍니다."
          checked={alarmSettings?.nearbyAlarm}
          onCheckedChange={(value) => handleSwitchChange("nearbyAlarm", value)}
        >
          <LocationAlarmIcon width={50} height={50} />
        </SetAlarmContent>
        <SetAlarmContent
          title="쪽지 알림"
          content="누군가 나에게 쪽지를 보내면 알려줍니다."
          checked={alarmSettings?.messageAlarm}
          onCheckedChange={(value) => handleSwitchChange("messageAlarm", value)}
        >
          <MessageAlarmIcon width={50} height={50} />
        </SetAlarmContent>
        <SetAlarmContent
          title="질문 등록 알림"
          content="내가 쓴 질문이 등록되면 알려줍니다."
          checked={alarmSettings?.addQuestionAlarm}
          onCheckedChange={(value) => handleSwitchChange("addQuestionAlarm", value)}
        >
          <QuestionAlarmIcon width={50} height={50} />
        </SetAlarmContent>
        <SetAlarmContent
          title="PICK 알림"
          content="누군가 나를 PICK하면 알려줍니다."
          checked={alarmSettings?.pickAlarm}
          onCheckedChange={(value) => handleSwitchChange("pickAlarm", value)}
        >
          <PickAlarmIcon width={50} height={50} />
        </SetAlarmContent>
      </div>
    </div>
  );
};

export default SetAlarm;
