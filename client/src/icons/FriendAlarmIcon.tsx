import { useNavigate } from "react-router-dom";

interface FriendAlarmIconProps {
  width: number;
  height: number;
}

const FriendAlarmIcon = ({width,height}:FriendAlarmIconProps) => {
  const navigate = useNavigate();
  const navigateToFriend = () => {
    navigate('/Friend ');
  }

  return (
    <img 
    onClick={navigateToFriend}
    width={width}
    height={height}
    src="icons/FriendAlarm.png" 
    alt="FriendAlarm" />
  )
}

export default FriendAlarmIcon;