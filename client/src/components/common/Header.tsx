import { useRecoilValue } from 'recoil';
import AlarmIcon from '../../icons/AlarmIcon';
import CoinIcon from '../../icons/CoinIcon';
import Logo from '../../icons/Logo';

import { userCoinState } from 'atoms/UserAtoms';
import { Link } from 'react-router-dom';


const Header = () => {
  const coin = useRecoilValue(userCoinState);
  return (
    <header className="flex flex-row justify-between mx-2.5 my-5">
      <Link to="/home">
        <Logo />
      </Link>

      <div className="flex flex-row justify-between items-center space-x-2">
        <div className="flex flex-row items-center">
          <CoinIcon width={25} height={25} />
          <span className="ml-1 text-sm font-bold text-gray-800">
            {/* {{Number(100)}.toLocaleString('ko-kr')} */}
            {coin}
          </span>
        </div>
        <Link to="/alarm">
          <AlarmIcon className="" />
        </Link>
      </div>
    </header>
  );
};

export default Header;
