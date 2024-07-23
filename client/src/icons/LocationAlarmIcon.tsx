import { useNavigate } from "react-router-dom";
interface LocationAlarmIconProps {
  width: number;
  height: number;
}

const LocationAlarmIcon = ({ width, height }: LocationAlarmIconProps) => {
  const navigate = useNavigate();
  const navigateToLocationAlarm = () => {
    navigate("/LocationAlarm");
  };
  return (
    <img
      onClick={navigateToLocationAlarm}
      className="cursor-pointer"
      width={width}
      height={height}
      src="/icons/Location.png"
      alt="location"
    />
  );
};

export default LocationAlarmIcon;
