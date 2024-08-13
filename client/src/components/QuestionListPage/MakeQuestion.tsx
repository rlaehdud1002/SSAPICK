import { useQuery } from '@tanstack/react-query';
import MakeQuestionContent from './MakeQuestionlContent';
import { getQuestionByUser } from 'api/questionApi';
import { IMadeQuestion, IQuestion } from 'atoms/Pick.type';
import Loading from 'components/common/Loading';
import QuestionPlusModal from 'components/modals/QuestionPlusModal';

const MakeQuestion = () => {

  const { data: questions, isLoading } = useQuery<IMadeQuestion[]>({
    queryKey: ['questions'],
    queryFn: async () => await getQuestionByUser(),
  
  });

  if (isLoading || !questions) {
    return <Loading />;
  }

  console.log('questions : ', questions);

  return (
    <div className="mb-20">
      <QuestionPlusModal location="myquestion" />
      {questions.length !== 0 ? (
        questions.map((question, index) => (
          <MakeQuestionContent key={index} question={question} questionId={question.id} deletable={question.deletable} />
        ))
      ) : (
        <div className="flex justify-center my-7">
          생성한 질문이 없습니다.
        </div>
      )}
    </div>
  );
};

export default MakeQuestion;
