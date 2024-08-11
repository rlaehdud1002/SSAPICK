import { useQueryClient } from "@tanstack/react-query";
import { IPick } from "atoms/Pick.type";
import UserMaskIcon from "icons/UserMaskIcon";

interface AlarmedQuestionProps {
  pick: IPick;
}

const AlarmedQuestion = ({ pick }: AlarmedQuestionProps) => {
  const queryClient = useQueryClient();

  const handleAlarmUpdate = (pickId: number) => {
    console.log(pickId);
    queryClient.invalidateQueries({ queryKey: ["pick"] });
  };

  return (
    <div>
      <div className="flex mt-5 ml-5">
        <UserMaskIcon
          pickId={pick.id}
          alarm={pick.alarm}
          gen={pick.sender.gender}
          onAlarmUpdate={handleAlarmUpdate}
        />
        <span className="ml-10">{pick.question.content}</span>
        {/* <Separator className="my-4 mx-4" />  */}
      </div>
      {/* <div className="bg-white h-px w-90 mx-2 mt-5"></div> */}
    </div>
  );
};

export default AlarmedQuestion;
