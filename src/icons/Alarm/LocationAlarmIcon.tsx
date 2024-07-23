interface LocationAlarmIconProps {
  width: number;
  height: number;
}

const LocationAlarmIcon = ({ width, height }: LocationAlarmIconProps) => {
  return (
    <img
      className="cursor-pointer"
      width={width}
      height={height}
      src="/icons/Location.png"
      alt="location"
    />
  );
};

export default LocationAlarmIcon;
