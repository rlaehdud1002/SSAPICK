import { Link } from "react-router-dom";

const NoFourFriends = () => {
  return (
    <Link to="/profile/friendsearch">
      <div className="bg-white hover:bg-white/80 rounded-lg py-2 px-10 text-center">
        친구 추가 하러 가기!
      </div>
    </Link>
  );
};

export default NoFourFriends;
