import { useQuery } from '@tanstack/react-query';
import MakeQuestionContent from './MakeQuestionlContent';
import { getQuestionByUser } from 'api/questionApi';
import { IQuestionNoCreatedAt } from 'atoms/Pick.type';
import Loading from 'components/common/Loading';

const MakeQuestion = () => {
  const { data: questions, isLoading } = useQuery<IQuestionNoCreatedAt[]>({
    queryKey: ['question', 'me'],
    queryFn: async () => await getQuestionByUser(),
  });

  if (isLoading) {
    return <Loading />;
  }

  console.log('questions : ', questions);

  if (!questions || questions.length === 0) {
    return (
      <div className="text-sm flex justify-center">생성한 질문이 없습니다.</div>
    );
  }

  return (
    <div className="mb-20">
      {questions.map((question, index) => (
        <MakeQuestionContent key={index} question={question.content} />
      ))}
    </div>
  );
};

export default MakeQuestion;
