interface AccountIconProps {
  width: number;
  height: number;
}

const AccountIcon = ({width, height}:AccountIconProps) => {
  return <img 
  width={width}
  height={height}
  src="icons/Account.png" 
  alt="account" />
}

export default AccountIcon;