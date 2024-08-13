interface RankingPickIconProps {
  width: number;
  height: number;
}

const RankingPickIcon = ({ width, height }: RankingPickIconProps) => {
  return (
    <img
      className="cursor-pointer"
      width={width}
      height={height}
      src="/icons/RankingPick.png"
      alt="RankingPick"
    />
  );
};

export default RankingPickIcon;
