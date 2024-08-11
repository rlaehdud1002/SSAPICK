import BackIcon from 'icons/BackIcon';
import SetAlarmIcon from 'icons/SetAlarmIcon';
import LocationAlarmIcon from 'icons/LocationAlarmIcon';
import MessageAlarmIcon from 'icons/MessageAlarmIcon';
import QuestionAlarmIcon from 'icons/QuestionAlarmIcon';
import PickAlarmIcon from 'icons/PickAlarmIcon';

import SetAlarmContent from 'components/SetAlarmPage/SetAlarmContent';

import { Switch } from 'components/ui/switch';
import { useNavigate } from 'react-router-dom';
import { IAlarm, IAlarmAll } from 'atoms/Alarm.type';
import { getAlarm, postAlarm, postAlarmAll } from 'api/alarmApi';
import { useEffect, useState } from 'react';
import { useMutation, useQuery } from '@tanstack/react-query';
import Loading from 'components/common/Loading';

const SetAlarm = () => {
  const {
    data: alarmSettings,
    isLoading,
    refetch,
  } = useQuery({
    queryKey: ['alarm'],
    queryFn: getAlarm,
  });

  console.log('alarmSettings 1 : ', alarmSettings);

  const [nearbyAlarm, setNearbyAlarm] = useState(alarmSettings?.nearbyAlarm);
  const [messageAlarm, setMessageAlarm] = useState(alarmSettings?.messageAlarm);
  const [addQuestionAlarm, setAddQuestionAlarm] = useState(
    alarmSettings?.addQuestionAlarm,
  );
  const [pickAlarm, setPickAlarm] = useState(alarmSettings?.pickAlarm);
  const [allAlarmSettings, setAllAlarm] = useState(
    alarmSettings?.nearbyAlarm &&
      alarmSettings?.messageAlarm &&
      alarmSettings?.addQuestionAlarm &&
      alarmSettings?.pickAlarm,
  );

  const nav = useNavigate();

  const postAlarmMutation = useMutation({
    mutationFn: (alarmData: IAlarm) => postAlarm(alarmData),
    onSuccess: () => {
      console.log('알람 설정 성공');
      refetch();
    },
    onError: (error) => {
      console.error('알람 업데이트 실패', error);
    },
  });

  const postAlarmAllMutation = useMutation({
    mutationFn: (updateAll: IAlarmAll) => postAlarmAll(updateAll),
    onSuccess: () => {
      console.log('전체 알람 설정 성공');
    },
    onError: (error) => {
      console.error('전체 알람 업데이트 실패', error);
    },
  });

  const handleSwitchChange = (key: keyof IAlarm, value: boolean) => {
    if (alarmSettings) {
      const updatedSettings = { ...alarmSettings, [key]: value };
      postAlarmMutation.mutate(updatedSettings);
      switch (key) {
        case 'nearbyAlarm':
          setNearbyAlarm(value);
          break;
        case 'messageAlarm':
          setMessageAlarm(value);
          break;
        case 'addQuestionAlarm':
          setAddQuestionAlarm(value);
          break;
        case 'pickAlarm':
          setPickAlarm(value);
          break;
        default:
          break;
      }
    }
  };

  const handleSwitchAllChange = (value: boolean) => {
    console.log('Alarm All set value', value);
    setNearbyAlarm(value);
    setMessageAlarm(value);
    setAddQuestionAlarm(value);
    setPickAlarm(value);
    setAllAlarm(value);
    postAlarmAllMutation.mutate({ onOff: value });
  };

  useEffect(() => {
    if (alarmSettings) {
      setNearbyAlarm(alarmSettings.nearbyAlarm);
      setMessageAlarm(alarmSettings.messageAlarm);
      setAddQuestionAlarm(alarmSettings.addQuestionAlarm);
      setPickAlarm(alarmSettings.pickAlarm);
      setAllAlarm(
        alarmSettings.nearbyAlarm &&
          alarmSettings.messageAlarm &&
          alarmSettings.addQuestionAlarm &&
          alarmSettings.pickAlarm,
      );
    }
  }, [alarmSettings]);

  useEffect(() => {
    setAllAlarm(nearbyAlarm && messageAlarm && addQuestionAlarm && pickAlarm);
  }, [nearbyAlarm, messageAlarm, addQuestionAlarm, pickAlarm]);

  if (isLoading) {
    return <Loading />;
  }

  return (
    <div className="flex flex-col">
      <div
        className="flex flex-row items-center m-2 cursor-pointer"
        onClick={() => nav(-1)}
      >
        <BackIcon />
        <SetAlarmIcon width={25} height={25} alarmset className="me-2 mt-1" />
        <h1>알림 관리</h1>
      </div>
      <div className="bg-white/50 rounded-lg my-6 mx-8 py-1.5 ps-5 pe-4 flex flex-row items-center justify-between">
        <span className="mx-2">전체 알림</span>
        <Switch
          checked={allAlarmSettings}
          onCheckedChange={(value) => handleSwitchAllChange(value)}
        />
      </div>
      <div className="mx-8">
        <SetAlarmContent
          title="내 주위 알림"
          content="내가 선택한 사람이 반경 10M 이내로 다가오면 알려줍니다."
          checked={nearbyAlarm}
          onCheckedChange={(value) => handleSwitchChange('nearbyAlarm', value)}
        >
          <LocationAlarmIcon width={50} height={50} />
        </SetAlarmContent>
        <SetAlarmContent
          title="쪽지 알림"
          content="누군가 나에게 쪽지를 보내면 알려줍니다."
          checked={messageAlarm}
          onCheckedChange={(value) => handleSwitchChange('messageAlarm', value)}
        >
          <MessageAlarmIcon width={50} height={50} />
        </SetAlarmContent>
        <SetAlarmContent
          title="질문 등록 알림"
          content="내가 쓴 질문이 등록되면 알려줍니다."
          checked={addQuestionAlarm}
          onCheckedChange={(value) =>
            handleSwitchChange('addQuestionAlarm', value)
          }
        >
          <QuestionAlarmIcon width={50} height={50} />
        </SetAlarmContent>
        <SetAlarmContent
          title="PICK 알림"
          content="누군가 나를 PICK하면 알려줍니다."
          checked={pickAlarm}
          onCheckedChange={(value) => handleSwitchChange('pickAlarm', value)}
        >
          <PickAlarmIcon width={50} height={50} />
        </SetAlarmContent>
      </div>
    </div>
  );
};

export default SetAlarm;
