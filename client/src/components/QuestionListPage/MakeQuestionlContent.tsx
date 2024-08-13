import { useMutation, useQueryClient } from '@tanstack/react-query';
import { deleteQuetion } from 'api/questionApi';
import { IQuestion } from 'atoms/Pick.type';

interface MakeQuestionContentProps {
  question: IQuestion;
  questionId: number;
  deletable: boolean;
}

const MakeQuestionContent = ({ question, questionId, deletable }: MakeQuestionContentProps) => {
  const queryClient = useQueryClient();
  const mutation = useMutation({
    mutationKey: ['deleteQuestion'],
    mutationFn: deleteQuetion,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['questions'] });
      console.log('질문 삭제 성공');
    },
  })

  return (
    <div>
      <div className="flex justify-between items-center py-5 border-b-[1px] border-white">
        <img
          src={question.category.thumbnail}
          alt="noImage"
          width="60"
          height="60"
          className="bg-white/50 rounded-full p-2 mx-4"
        />
        <div className='w-40'>{question.content}</div>
        {/* {deletable ? (
          <button onClick={() => {
            mutation.mutate(questionId)
          }} className="flex justify-center bg-[#5F86E9] text-white rounded-lg w-20">
            <span >
              삭제
            </span>
          </button>) : (
          <div className="flex justify-center bg-gray-400 text-white rounded-lg w-20">
            <span>
              삭제 불가
            </span>
          </div>
        )} */}
        <div></div>

      </div>
    </div>
  );
};

export default MakeQuestionContent;
