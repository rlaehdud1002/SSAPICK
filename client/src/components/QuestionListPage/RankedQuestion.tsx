import { useQuery } from '@tanstack/react-query';
import { getMyQuestionRank } from 'api/questionApi';
import { IPick, IQuestion } from 'atoms/Pick.type';
import Loading from 'components/common/Loading';
import TrophyIcon from 'icons/TrophyIcon';

const RankedQuestion = () => {
  const { data: rankQuestion, isLoading } = useQuery<IQuestion[]>({
    queryKey: ['pick', 'rank'],
    queryFn: async () => await getMyQuestionRank(),
  });

  if (isLoading || !rankQuestion) {
    return <Loading />;
  }

  console.log('rankQuestion', rankQuestion);

  const rankColor = ['text-[#D5A11E]', 'text-[#A3A3A3]', 'text-[#CD7F32]'];

  return (
    <div className="mb-5 pe-4 pb-5 border-b-[1px]">
      <div className="flex flex-row my-5">
        <TrophyIcon width={25} height={25} />
        <h1 className="ms-2">
          내가 지목된 질문{' '}
          <span className="luckiest_guy text-orange-400 text-lg">top 3</span>
        </h1>
      </div>
      {rankQuestion.length !== 0 ? (
        <div className="flex flex-col">
          {rankQuestion.slice(0, 5).map((question, index) => (
            <div className="flex flex-row justify-between mb-3 border-solid border-2 rounded-lg border-white/70">
              <div className="flex items-center flex-row my-2 mx-4">
                <div className={`luckiest_guy text-lg ${rankColor[index]}`}>
                  {index + 1}
                </div>
                <div className="ms-2 break-words">{question.content}</div>
              </div>
            </div>
          ))}
        </div>
      ) : (
        <div className="text-center text-sm my-24">
          랭킹을 위한 정보가 부족합니다.
        </div>
      )}
    </div>
  );
};

export default RankedQuestion;
