import AlarmedIcon from "icons/AlarmedIcon";
import { Separator } from "@radix-ui/react-select";

interface AlarmedQuestionProps {
  gender: string;
  title: string;
}

const AlarmedQuestion = ({ gender, title }: AlarmedQuestionProps) => {
  return (
    <div>
      <div className="flex mt-5 ml-5">
        <AlarmedIcon gender={gender} />
        <span className="ml-10">{title}..</span>
        {/* <Separator className="my-4 mx-4" />  */}
      </div>
      <div className="bg-white h-px w-90 mx-2 mt-5"></div>
    </div>
  );
};

export default AlarmedQuestion;
