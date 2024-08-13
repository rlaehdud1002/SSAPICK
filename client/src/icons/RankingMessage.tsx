interface RankingMessageIconProps {
  width: number;
  height: number;
}

const RankingMessageIcon = ({ width, height }: RankingMessageIconProps) => {
  return (
    <img
      className="cursor-pointer"
      width={width}
      height={height}
      src="/icons/RankingMessage.png"
      alt="RankingMessage"
    />
  );
};

export default RankingMessageIcon;
