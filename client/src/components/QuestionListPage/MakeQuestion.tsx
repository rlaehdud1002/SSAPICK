import { useQuery } from '@tanstack/react-query';
import MakeQuestionContent from './MakeQuestionlContent';
import { getQuestionByUser } from 'api/questionApi';
import { IQuestion } from 'atoms/Pick.type';
import Loading from 'components/common/Loading';

const MakeQuestion = () => {
  const { data: questions, isLoading } = useQuery<IQuestion[]>({
    queryKey: ['question', 'me'],
    queryFn: async () => await getQuestionByUser(),
  });

  if (isLoading || !questions) {
    return <Loading />;
  }

  console.log('questions : ', questions);

  return (
    <div className="mb-20">
      <div className="bg-ssapick rounded-lg text-center mt-4 p-4">
        질문 생성하기!
      </div>
      {questions.length !== 0 ? (
        questions.map((question, index) => (
          <MakeQuestionContent key={index} question={question} />
        ))
      ) : (
        <div className="text-sm flex justify-center">
          생성한 질문이 없습니다.
        </div>
      )}
    </div>
  );
};

export default MakeQuestion;
