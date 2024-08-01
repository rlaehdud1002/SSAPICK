interface TrophyIconProps {
  width: number;
  height: number;
}

const TrophyIcon = ({ width, height }: TrophyIconProps) => {
  return (
    <img
      className="cursor-pointer"
      width={width}
      height={height}
      src="/icons/Trophy.png"
      alt="Trophy"
    />
  );
};

export default TrophyIcon;
