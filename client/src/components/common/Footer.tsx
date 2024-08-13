import HomeIcon from '../../icons/HomeIcon';
import MessageIcon from '../../icons/MessageIcon';
import RankingIcon from '../../icons/RankingIcon';
import PickIcon from '../../icons/PickIcon';
import ProfileIcon from '../../icons/ProfileIcon';

import { Link, useLocation } from 'react-router-dom';
import { useEffect } from 'react';

const Footer = () => {
  const location = useLocation().pathname.split('/')[1];

  return (
    <div className="fixed bottom-0 bg-white h-[70px]">
      <div className="flex flex-row justify-around h-20 p-4 items-center w-screen">
        <Link to="/home">
          <HomeIcon isHighlighted={location === 'home'} />
        </Link>
        <Link to="/ranking">
          <RankingIcon isHighlighted={location === 'ranking'} />
        </Link>
        <Link to="/pick">
          <PickIcon isHighlighted={location === 'pick'} />
        </Link>
        <Link to="/message">
          <MessageIcon isHighlighted={location === 'message'} />
        </Link>
        <Link to="/profile">
          <ProfileIcon isHighlighted={location === 'profile'} />
        </Link>
      </div>
    </div>
  );
};

export default Footer;
