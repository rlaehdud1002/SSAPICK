import { Separator } from "@radix-ui/react-select";
import PlusDeleteButton from "buttons/PlusDeleteButton"
import QuestionAlarmIcon from "icons/QuestionAlarmIcon";

interface BlockQuestionContentProps{
    question:string
}

const BlockQuestionContent = ({question}:BlockQuestionContentProps) =>{
    const onEvnet = ()=>{
        console.log("차단 질문 리스트에서 삭제")
    }
    return (
        <div>
        <div className="flex items-center mt-5 justify-between mx-8">
      <div>
      <QuestionAlarmIcon width={50} height={50}/>
      </div>
      <div className="">{question}</div>
        <div onClick={onEvnet}>
        <PlusDeleteButton title="삭제" />
        </div>
    </div>
    {/* <Separator className="my-4 mx-4" />  */}
    <div className="bg-white h-px w-90 mx-8 mt-5"></div>
    </div>
    )
}

export default BlockQuestionContent