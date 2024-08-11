import { Separator } from '@radix-ui/react-select';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { blockQuestionCancel } from 'api/blockApi';
import PlusDeleteButton from 'buttons/PlusDeleteButton';
import QuestionAlarmIcon from 'icons/QuestionAlarmIcon';

interface BlockQuestionContentProps {
  question: any;
  questionId: number;
  thumbnail: string ;
}

const BlockQuestionContent = ({
  question,
  questionId,
  thumbnail,
}: BlockQuestionContentProps) => {
  const queryClient = useQueryClient();
  const mutation = useMutation({
    mutationKey: ['deleteBlock'],
    mutationFn: blockQuestionCancel,

    onSuccess: () => {
      console.log('차단 해제 성공');
      queryClient.invalidateQueries({
        queryKey: ['blockQuestion'],
      });
    },
  });

  return (
    <div>
      <div className="flex items-center mt-5 justify-between mx-8">
        <div className='bg-white/60 w-16 h-16 flex justify-center items-center rounded-full'>
          <img className='w-12 h-12' src={thumbnail} alt="thumbnail" />
        </div>
        <div className="">{question}</div>
        <div
          onClick={() => {
            mutation.mutate(questionId);
          }}
        >
          <PlusDeleteButton title="삭제" />
        </div>
      </div>
      {/* <Separator className="my-4 mx-4" />  */}
      <div className="bg-white h-px w-90 mx-8 mt-5"></div>
    </div>
  );
};

export default BlockQuestionContent;
