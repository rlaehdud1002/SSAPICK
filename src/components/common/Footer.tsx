import HomeIcon from '../../icons/HomeIcon';
import MessageIcon from '../../icons/MessageIcon';
import RankingIcon from '../../icons/RankingIcon';
import PickIcon from '../../icons/PickIcon';
import ProfileIcon from '../../icons/ProfileIcon';

const Footer = () => {
  let location = 'hoe';
  return (
    <div className="fixed bottom-0">
      <div className="flex flex-row justify-around h-14 bg-white px-4 py-4 items-center w-screen ">
        <HomeIcon isHighlighted={location === 'home'} />
        <RankingIcon isHighlighted={location === 'ranking'} />
        <PickIcon isHighlighted={location === 'pick'} />
        <MessageIcon isHighlighted={location === 'message'} />
        <ProfileIcon isHighlighted={location === 'profile'} />
      </div>
    </div>
  );
};

export default Footer;
