import AlarmedQuestion from "./AlarmedQuestion";
import QuestionInfoContent from "./RankedQuestion";

const QuestionInfo = () => {
  return (
    <div className="ml-5">
      <QuestionInfoContent />
      {/* <Separator className="my-4 mx-4" />  */}
      <div className="bg-white h-px w-90 mx-1 mt-5"></div>
      <div className="mt-5">
        <span>알림 설정한 질문</span>
        <div className="mb-20">
          {[0].map((index) => (
            <AlarmedQuestion key={index} gender="여자" title="같이 밥먹고 싶은 사람?" />
          ))}
        </div>
      </div>
    </div>
  );
};

export default QuestionInfo;
