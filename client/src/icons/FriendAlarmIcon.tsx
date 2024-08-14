interface FriendAlarmIconProps {
  width: number;
  height: number;
}

const FriendAlarmIcon = ({ width, height }: FriendAlarmIconProps) => {
  return (
    <img
      width={width}
      height={height}
      src="icons/FriendAlarm.png"
      alt="FriendAlarm"
    />
  );
};

export default FriendAlarmIcon;
