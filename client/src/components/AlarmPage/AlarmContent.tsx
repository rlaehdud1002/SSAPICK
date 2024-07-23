import LocationAlarmIcon from 'icons/LocationAlarmIcon';
import MessageAlarmIcon from 'icons/MessageAlarmIcon';
import PickAlarmIcon from 'icons/PickAlarmIcon';
import QuestionAlarmIcon from 'icons/QuestionAlarmIcon';

interface AlarmContentProps {
  category: string;
  content: string;
  read: boolean;
}

const AlarmContent = ({ category, content, read }: AlarmContentProps) => {
  return (
    <div className="bg-white/50 rounded-md flex flex-row p-3">
      <div>
        {category === 'location' && (
          <LocationAlarmIcon width={42} height={42} />
        )}
        {category === 'message' && <MessageAlarmIcon width={42} height={42} />}
        {category === 'pick' && <PickAlarmIcon width={42} height={42} />}
        {category === 'question' && (
          <QuestionAlarmIcon width={42} height={42} />
        )}
      </div>
      <div className="flex flex-col ms-3">
        <p>{content}</p>
        <div className="flex flex-row">
          <p className="text-xs text-gray-500">1분 전</p>
          {read && (
            <p className="text-xs text-red-500 luckiest_guy bg-white/50 rounded-full px-1 ms-1">
              N
            </p>
          )}
        </div>
      </div>
    </div>
  );
};

export default AlarmContent;
