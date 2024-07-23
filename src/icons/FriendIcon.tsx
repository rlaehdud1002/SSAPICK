interface FriendIconProps {
  width: number;
  height: number;
}

const FriendIcon = ({width,height}:FriendIconProps) => {
  return (
    <img
    width={width}
    height={height}
    src="/icons/Friend.png"
    alt="friend"
  />
  )
}

export default FriendIcon;