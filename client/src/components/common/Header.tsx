import { Link } from "react-router-dom";
import AlarmIcon from "../../icons/AlarmIcon";
import Logo from "../../icons/Logo";
import MoveGuide from "components/GuidePage/MoveGuide";

const Header = () => {
  return (
    <header className="flex flex-row justify-between items-center mx-2.5 my-5">
      <Link to="/home">
        <Logo />
      </Link>
      <div className="flex">
        <div>
          <MoveGuide />
        </div>
        <div className="mx-4">
          <Link to="/alarm">
            <AlarmIcon />
          </Link>
        </div>
      </div>
    </header>
  );
};

export default Header;
