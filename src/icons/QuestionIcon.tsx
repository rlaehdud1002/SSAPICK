interface QuestionImageIconProps {
  width: number;
  height: number;
}

const QuestionImageIcon = ({ width, height }: QuestionImageIconProps) => {
  return (
    <img
      className="cursor-pointer"
      width={width}
      height={height}
      src="/icons/QuestionImage.png"
      alt="questionimage"
    />
  );
};

export default QuestionImageIcon;
