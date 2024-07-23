import { useNavigate } from "react-router-dom";

interface AccountIconProps {
  width: number;
  height: number;
}

const AccountIcon = ({width, height}:AccountIconProps) => {
  const navigate = useNavigate();
  const navigateToSetAccount = () => {
    navigate('/SetAccount');
  }
  return <img 
  onClick={navigateToSetAccount}
  width={width}
  height={height}
  src="icons/Account.png" 
  alt="account" />
}

export default AccountIcon;