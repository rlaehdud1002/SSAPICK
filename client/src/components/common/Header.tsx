import { Link } from 'react-router-dom';
import AlarmIcon from '../../icons/AlarmIcon';
import Logo from '../../icons/Logo';

const Header = () => {
  return (
    <header className="flex flex-row justify-between items-center mx-2.5 my-5">
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
