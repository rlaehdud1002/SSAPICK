interface QuestionAlarmIconProps {
  width: number;
  height: number;
}

const QuestionAlarmIcon = ({ width, height }: QuestionAlarmIconProps) => {
  return (
    <img
      className="cursor-pointer"
      width={width}
      height={height}
      src="/icons/Question.png"
      alt="Question"
    />
  );
};

export default QuestionAlarmIcon;
