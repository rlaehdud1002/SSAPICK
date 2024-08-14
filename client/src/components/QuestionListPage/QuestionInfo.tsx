import { useQuery } from '@tanstack/react-query';
import AlarmedQuestion from './AlarmedQuestion';
import QuestionInfoContent from './RankedQuestion';
import { IPick } from 'atoms/Pick.type';
import { getAlarmPick } from 'api/pickApi';
import Loading from 'components/common/Loading';

const QuestionInfo = () => {
  const { data: alarmPick, isLoading } = useQuery<IPick>({
    queryKey: ['pick'],
    queryFn: () => getAlarmPick(),
  });

  if (isLoading) {
    return <Loading />;
  }

  return (
    <div className="mt-3">
      <div className="my-5">
        <span className="flex justify-center">알림 설정한 질문</span>
        {alarmPick ? (
          <div className="mt-5">
            <AlarmedQuestion pick={alarmPick} />
          </div>
        ) : (
          <div className="text-center text-sm my-14">
            알림 설정한 질문이 없습니다
          </div>
        )}
      </div>
      <div className="ml-5">
        <QuestionInfoContent />
      </div>
    </div>
  );
};

export default QuestionInfo;
