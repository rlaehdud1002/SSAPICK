import { useQuery } from "@tanstack/react-query";
import AlarmedQuestion from "./AlarmedQuestion";
import QuestionInfoContent from "./RankedQuestion";
import { IPick } from "atoms/Pick.type";
import { getAlarmPick } from "api/pickApi";
import Loading from "components/common/Loading";

const QuestionInfo = () => {
  const { data: alarmPick, isLoading } = useQuery<IPick>({
    queryKey: ["pick"],
    queryFn: () => getAlarmPick(),
  });

  console.log("alarmPick", alarmPick);

  if (isLoading) {
    return <Loading />;
  }

  return (
    <div className="ml-5">
      <QuestionInfoContent />
      {/* <Separator className="my-4 mx-4" />  */}
      <div className="bg-white h-px w-90 mx-1 mt-5"></div>
      <span>알림 설정한 질문</span>
      {alarmPick && (
        <div className="mt-5">
          <div className="mb-20">
            <AlarmedQuestion pick={alarmPick} />
          </div>
        </div>
      )}
    </div>
  );
};

export default QuestionInfo;
