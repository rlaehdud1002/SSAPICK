import { useRecoilValue } from 'recoil';
import AlarmIcon from '../../icons/AlarmIcon';
import Logo from '../../icons/Logo';
import { userCoinState } from 'atoms/UserAtoms';
import { Link } from 'react-router-dom';
import CoinIcon from 'icons/CoinIcon';

const Header = () => {
  const coin = useRecoilValue(userCoinState);
  return (
    <header className="flex flex-row justify-between mx-2.5 my-5">
      <Link to="/home">
        <Logo />
      </Link>
      <Link to="/alarm">
        <AlarmIcon />
      </Link>
    </header>
  );
};

export default Header;
