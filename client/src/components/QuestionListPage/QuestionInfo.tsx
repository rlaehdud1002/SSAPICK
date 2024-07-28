import AlarmedQuestion from "./AlarmedQuestion";
import QuestionInfoContent from "./RankedQuestion"

const QuestionInfo = () => {
    const fakeList = [
        {
          rank: 1,
          content: '질문 1',
        },
        {
          rank: 2,
          content: '질문 2',
        },
        {
          rank: 3,
          content: '질문 3',
        },
      ];
    return (
    <div className="ml-5">
     <QuestionInfoContent title="내가 지목된 질문" rankList={fakeList}/>
     {/* <Separator className="my-4 mx-4" />  */}
    <div className="bg-white h-px w-90 mx-1 mt-5"></div>
    <div className="mt-5">
    <span>알림 설정한 질문</span>
    <div className="mb-20">
    {[0, 1, 2, 3, 4, 5,6,6].map((index) => (
            <AlarmedQuestion key={index} gender="여자" title="같이 밥먹고 싶은 사람?"/>
          ))}
    </div>
    </div>
     </div>
    )
}

export default QuestionInfo