import { IQuestion } from 'atoms/Pick.type';

interface MakeQuestionContentProps {
  question: IQuestion;
}

const MakeQuestionContent = ({
  question,
}: MakeQuestionContentProps) => {
  return (
    <div className="flex items-center py-5 border-b-[1px] border-white">
      <img
        src={question.category.thumbnail}
        alt="noImage"
        width="60"
        height="60"
        className="bg-white/50 rounded-full p-2 mx-4"
      />
      <div className="mx-2 w-full">{question.content}</div>
    </div>
  );
};

export default MakeQuestionContent;
