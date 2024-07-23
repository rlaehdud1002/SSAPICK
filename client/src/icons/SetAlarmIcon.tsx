import { useNavigate } from "react-router-dom";


interface SetAlarmIconProps {
  width: number;
  height: number;
}

const SetAlarmIcon = ({width,height}:SetAlarmIconProps) => {
  const navigate = useNavigate();
  const navigateToSetAlarm = () => {
    navigate('/SetAlarm');
  }
  return <img
  onClick={navigateToSetAlarm}
  width={width}
  height={height} 
  src="icons/Alarm.png" 
  alt="alram" />
}


export default SetAlarmIcon;
