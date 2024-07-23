interface ProfilePickIconProps {
  width: number;
  height: number;

}

const ProfilePickIcon = ({width,height}:ProfilePickIconProps)=>{
  return <img
  className="cursor-pointer"
  width={width}
  height={height}
  src="/icons/Pick.png"
  alt="coin"
/>
}

export default ProfilePickIcon;