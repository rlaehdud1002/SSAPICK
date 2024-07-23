import { useNavigate } from "react-router-dom";

interface QuestionAlarmIconProps {
  width: number;
  height: number;
}

const QuestionAlarmIcon = ({ width, height }: QuestionAlarmIconProps) => {
  const navigate = useNavigate();
  const navigateToQuestionList = () => {
    navigate("/QuestionList");
  };
  return (
    <img
      onClick={navigateToQuestionList}
      className="cursor-pointer"
      width={width}
      height={height}
      src="/icons/Question.png"
      alt="Question"
    />
  );
};

export default QuestionAlarmIcon;
