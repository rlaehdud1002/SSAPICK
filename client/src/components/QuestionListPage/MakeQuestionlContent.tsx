import DoneButton from "buttons/DoneButton"
import { FILE } from "dns"
import QuestionAlarmIcon from "icons/QuestionAlarmIcon"
import { Link } from "react-router-dom"

interface MakeQuestionContentProps{
    // question:string
    testList:Array<{question:string}>
}

const MakeQuestionContent = ({testList}:MakeQuestionContentProps) => {
    
    return (
        <div>
        {testList.length !== 0 ? (
            testList.map((testItem, index) => {
                return (
                    <div className="flex flex-col">
                        <div className="flex items-center my-5 ml-5"
                            key={index}
                        >
                            <QuestionAlarmIcon width={50} height={50}/>
                            <span className="ml-3">{testItem.question}</span>
                        </div>
                        <div className="bg-white h-px w-90 mr-5"></div>
                    </div>
                )
            }
        )):(
            <div className="flex flex-col items-center">
            <div className="mt-32 mb-10">생성한 질문이 없습니다.</div>
            <div className="my-10">
            <Link to="/pick">
            <DoneButton title="나만의 질문 만들러 가기"/>
            </Link>
            </div>
            </div>
        )}
        </div>

    //     <div>
    //         {/* <div className="flex items-center mt-5 justify-between mx-12">
    //             <div>
    //             <QuestionAlarmIcon width={50} height={50}/>
    //             </div>
    //             <div>{question}</div>
    //         </div>
    //         {/* <Separator className="my-4 mx-4" />  *
    //         {/* <div className="bg-white h-px w-90 mx-2 mt-5"></div> */} 
    // </div>
    )
}

export default MakeQuestionContent