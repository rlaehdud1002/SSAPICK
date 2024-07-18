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
      src="/icons/trophy.png"
      alt="trophy"
    />
  )
}

export default TrophyIcon 