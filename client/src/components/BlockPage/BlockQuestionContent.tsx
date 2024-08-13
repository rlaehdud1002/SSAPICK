import { Separator } from "@radix-ui/react-select";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { blockQuestionCancel } from "api/blockApi";
import PlusDeleteButton from "buttons/PlusDeleteButton";
import BlockCancelModal from "components/modals/BlockCancelModal";
import QuestionAlarmIcon from "icons/QuestionAlarmIcon";

interface BlockQuestionContentProps {
  question: string;
  questionId: number;
  thumbnail: string;
}

const BlockQuestionContent = ({
  question,
  questionId,
  thumbnail,
}: BlockQuestionContentProps) => {

  return (
    <div>
      <div className="flex items-center mt-5 justify-between mx-8">
        <div className="bg-white/60 w-16 h-16 flex justify-center items-center rounded-full">
          <img className="w-12 h-12 rounded-full" src={thumbnail} alt="thumbnail" />
        </div>
        <div className="text-xs w-40">{question}</div>
        {/* <PlusDeleteButton title="삭제" /> */}
        <BlockCancelModal Id={questionId} category="question"/>
      </div>
      {/* <Separator className="my-4 mx-4" />  */}
      <div className="bg-white h-px w-90 mx-8 mt-5"></div>
    </div>
  );
};

export default BlockQuestionContent;
