import { useNavigate } from "react-router-dom";

interface UserInfoIconProps {
  width: number;
  height: number;
}



const UserInfoIcon = ({width,height}:UserInfoIconProps) => {
  const navigate = useNavigate();
  const navigateToModiUserInfo = () => {
    navigate('/ModiUserInfo ');
  }

  return(
    <img 
    onClick={navigateToModiUserInfo}
    width={width}
    height={height}
    src="icons/UserInfo.png" 
    alt="UserInfo" />
  )
}

export  default UserInfoIcon;