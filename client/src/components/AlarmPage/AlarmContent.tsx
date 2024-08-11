import { INotification } from 'atoms/Notification.type';
import LocationAlarmIcon from 'icons/LocationAlarmIcon';
import MessageAlarmIcon from 'icons/MessageAlarmIcon';
import PickAlarmIcon from 'icons/PickAlarmIcon';
import QuestionAlarmIcon from 'icons/QuestionAlarmIcon';

interface AlarmContentProps {
  notification: INotification;
}

const AlarmContent = ({ notification }: AlarmContentProps) => {
  const createTime = new Date(notification.createdAt).getTime();
  const currentTime = new Date().getTime();
  const diffTime = currentTime - createTime;

  const months = String(
    Math.floor((diffTime / (1000 * 60 * 60 * 24 * 30)) % 12),
  );
  const days = String(Math.floor((diffTime / (1000 * 60 * 60 * 24)) % 30));
  const hours = String(Math.floor((diffTime / (1000 * 60 * 60)) % 24));
  const minutes = String(Math.floor((diffTime / (1000 * 60)) % 60));

  let receivedTime;

  if (months !== '0') {
    receivedTime = `${months}개월 전`;
  } else if (days !== '0') {
    receivedTime = `${days}일 전`;
  } else if (hours !== '0') {
    receivedTime = `${hours}시간 전`;
  } else if (minutes !== '0') {
    receivedTime = `${minutes}분 전`;
  } else {
    receivedTime = '방금';
  }

  return (
    <div className="bg-white/50 rounded-lg flex flex-row p-3 mb-3">
      <div>
        {notification.type === 'LOCATION' && (
          <LocationAlarmIcon width={42} height={42} />
        )}
        {notification.type === 'MESSAGE' && (
          <MessageAlarmIcon width={42} height={42} />
        )}
        {notification.type === 'PICK' && (
          <PickAlarmIcon width={42} height={42} />
        )}
        {notification.type === 'ADD_QUESTION' && (
          <QuestionAlarmIcon width={42} height={42} />
        )}
      </div>
      <div className="flex flex-col ms-3 w-full">
        <p>{notification.title}</p>
        <div className="flex flex-row">
          <p className="text-xs text-gray-500">{receivedTime}</p>
          {!notification.read && (
            <p className="text-xs text-red-500 luckiest_guy bg-white/50 rounded-full px-1 ms-1 flex flex-row items-center">
              N
            </p>
          )}
        </div>
        <div className="text-center p-1 mt-2 flex flex-row justify-center min-w-4/5 max-w-full bg-[#92AEF4]/30 rounded-lg">
          <span className="text-[#4D5BDC]">{notification.message}</span>
        </div>
      </div>
    </div>
  );
};

export default AlarmContent;
