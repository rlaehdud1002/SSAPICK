import { Link } from 'react-router-dom';

const NoFourFriends = () => {
  return (
    <div className="flex flex-col items-center justify-center">
      <div className="my-56">
        <span className="luckiest_guy">PICK</span> 을 하려면 친구가 4명 이상
        필요해요!
      </div>
      <Link to="/profile/friendsearch">
        <div className="bg-ssapick rounded-lg py-2 px-10 text-center">
          친구 추가 하러 가기!
        </div>
      </Link>
    </div>
  );
};

export default NoFourFriends;
