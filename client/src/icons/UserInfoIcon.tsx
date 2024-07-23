interface UserInfoIconProps {
  width: number;
  height: number;
}



const UserInfoIcon = ({width,height}:UserInfoIconProps) => {
  return(
    <img 
    width={width}
    height={height}
    src="icons/UserInfo.png" 
    alt="UserInfo" />
  )
}

export  default UserInfoIcon;