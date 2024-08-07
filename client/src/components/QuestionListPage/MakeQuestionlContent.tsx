import PlusDeleteButton from "buttons/PlusDeleteButton";
import QuestionAlarmIcon from "icons/QuestionAlarmIcon";

interface MakeQuestionContentProps {
  question: string;
}

const MakeQuestionContent = ({ question }: MakeQuestionContentProps) => {
  return (
    <div>
      <div className="flex items-center mt-5 justify-between mx-12">
        <div>
          <QuestionAlarmIcon width={50} height={50} />
        </div>
        <div>{question}</div>
      </div>
      {/* <Separator className="my-4 mx-4" />  */}
      <div className="bg-white h-px w-90 mx-2 mt-5" />
    </div>
  );
};

export default MakeQuestionContent;
