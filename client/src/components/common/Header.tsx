import { useRecoilValue } from "recoil";
import AlarmIcon from "../../icons/AlarmIcon";
import CoinIcon from "../../icons/CoinIcon";
import Logo from "../../icons/Logo";
import { userCoinState } from "atoms/UserAtoms";
import { Link } from "react-router-dom";

const Header = () => {
  const coin = useRecoilValue(userCoinState);
  return (
    <header className="flex flex-row justify-between mx-2.5 my-5">
      <Link to="/home">
        <Logo />
      </Link>
      <div className="flex flex-row justify-between items-center space-x-2">
        <Link to="/alarm">
          <AlarmIcon />
        </Link>
      </div>
    </header>
  );
};

export default Header;
