import UserMaskIcon from "icons/UserMaskIcon";

interface AlarmedQuestionProps {
    // gender: string;
    checkedList: Array<{ question: string, gender: string }>;
}

const AlarmedQuestion = ({ checkedList }: AlarmedQuestionProps) => {
    return (
        <div className="flex flex-col mt-5 ml-5">
            {/* <span className="ml-10">{title}</span> */}
            {/* <Separator className="my-4 mx-4" />  */}
            {checkedList.length !== 0 ? (
                checkedList.map((checkedItem, index) => {
                    return (
                        <div className="flex flex-col">
                            <div className="flex mt-5 ml-5"
                                key={index}>
                                <UserMaskIcon checked={true} gen={checkedItem.gender} />
                                <span className="ml-3">{checkedItem.question}</span>
                            </div>
                            <div className="bg-white h-px w-90 mt-3 mr-5"></div>
                        </div>
                    )
                })) :
                (
                    <div className='text-center text-sm my-24'>알림 설정한 질문이 없습니다.</div>)}
        </div>
    )
}

export default AlarmedQuestion
