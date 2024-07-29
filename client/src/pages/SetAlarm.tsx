import BackIcon from 'icons/BackIcon';
import SetAlarmIcon from 'icons/SetAlarmIcon';
import LocationAlarmIcon from 'icons/LocationAlarmIcon';
import MessageAlarmIcon from 'icons/MessageAlarmIcon';
import QuestionAlarmIcon from 'icons/QuestionAlarmIcon';
import PickAlarmIcon from 'icons/PickAlarmIcon';

import SetAlarmContent from 'components/SetAlarmPage/SetAlarmContent';

import { Switch } from 'components/ui/switch';
import { useNavigate } from 'react-router-dom';

const SetAlarm = () => {
  const nav = useNavigate();
  return (
    <div className="flex flex-col">
      <div
        className="flex flex-row items-center m-2 cursor-pointer"
        onClick={() => nav(-1)}
      >
        <BackIcon />
        <SetAlarmIcon width={25} height={25} alarmset className="mr-2 mt-1" />
        <h1>알림 관리</h1>
      </div>
      <div className="bg-white/50 rounded-xl my-6 mx-8 mt-4 py-1.5 px-5 flex flex-row items-center justify-between">
        <span className="mx-2">전체 알림</span>
        <Switch />
      </div>
      <div className="mx-8">
        <SetAlarmContent
          title="내 주위 알림"
          content="내가 선택한 사람이 반경 10M 이내로 다가오면 알려줍니다."
        >
          <LocationAlarmIcon width={50} height={50} />
        </SetAlarmContent>
        <SetAlarmContent
          title="쪽지 알림"
          content="누군가 나에게 쪽지를 보내면 알려줍니다."
        >
          <MessageAlarmIcon width={50} height={50} />
        </SetAlarmContent>
        <SetAlarmContent
          title="질문 등록 알림"
          content="내가 쓴 질문이 등록되면 알려줍니다."
        >
          <QuestionAlarmIcon width={50} height={50} />
        </SetAlarmContent>
        <SetAlarmContent
          title="PICK 알림"
          content="누군가 나를 PICK하면 알려줍니다."
        >
          <PickAlarmIcon width={50} height={50} />
        </SetAlarmContent>
      </div>
    </div>
  );
};

export default SetAlarm;
