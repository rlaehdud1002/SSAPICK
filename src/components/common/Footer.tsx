import HomeIcon from '../../icons/HomeIcon';
import RankingIcon from '../../icons/RankingIcon';

const Footer = () => {
  let location = 'home';
  return (
      <div className="fixed bottom-0 flex flex-row justify-evenly h-14 bg-white px-8 py-4 items-center w-screen">
        <HomeIcon isHighlighted={location === 'home'} />
        <RankingIcon />
      </div>
  );
};

export default Footer;
