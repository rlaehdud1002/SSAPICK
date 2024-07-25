interface SetAlarmIconProps {
  width: number;
  height: number;
}

const SetAlarmIcon = ({width,height}:SetAlarmIconProps) => {
  return <img
  width={width}
  height={height} 
  src="icons/Alarm.png" 
  alt="alram" />
}


export default SetAlarmIcon;
