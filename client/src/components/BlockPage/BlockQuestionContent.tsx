import { Separator } from "@radix-ui/react-select";
import { useMutation } from "@tanstack/react-query";
import { blockQuestionCancel } from "api/blockApi";
import PlusDeleteButton from "buttons/PlusDeleteButton"
import QuestionAlarmIcon from "icons/QuestionAlarmIcon";

interface BlockQuestionContentProps{
    question:any
    questionId:number
}

const BlockQuestionContent = ({question, questionId}:BlockQuestionContentProps) =>{
   
    const mutation = useMutation({
        mutationKey: ['deleteBlock'],
        mutationFn: blockQuestionCancel,
    
        onSuccess: () => {
          console.log('차단 해제 성공');
        }
    });
    
    return (
        <div>
        <div className="flex items-center mt-5 justify-between mx-8">
      <div>
      <QuestionAlarmIcon width={50} height={50}/>
      </div>
      <div className="">{question}</div>
        <div onClick={()=>{mutation.mutate(questionId)}}>
        <PlusDeleteButton title="삭제" />
        </div>
    </div>
    {/* <Separator className="my-4 mx-4" />  */}
    <div className="bg-white h-px w-90 mx-8 mt-5"></div>
    </div>
    )
}

export default BlockQuestionContent