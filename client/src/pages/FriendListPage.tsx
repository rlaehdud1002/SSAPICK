import { useQuery } from '@tanstack/react-query';
import { getUserInfo } from 'api/authApi';
import { getFriendsList } from 'api/friendApi';
import { IFriend } from 'atoms/Friend.type';
import { IUserInfo } from 'atoms/User.type';
import Friend from 'components/FriendListPage/FriendBox';
import Loading from 'components/common/Loading';
import BackIcon from 'icons/BackIcon';
import FriendIcon from 'icons/FriendIcon';
import SearchIcon from 'icons/SearchIcon';
import { Link, useNavigate } from 'react-router-dom';

const FriendList = () => {
  // 친구 리스트 조회
  const { data: friends, isLoading } = useQuery<IFriend[]>({
    queryKey: ['friends'],
    queryFn: getFriendsList,
  });

  // console.log(friends);
  const navigate = useNavigate();

  if (isLoading) {
    return <Loading />;
  }

  return (
    <div className="mb-25">
      <div className="flex justify-between">
        <div className="flex ml-2">
          <div onClick={() => navigate(-1)} className="mr-2">
            <BackIcon />
          </div>
          <FriendIcon width={20} height={20} isDefault={true} />
          <div className="ml-2">친구목록</div>
        </div>
        <div className="mr-6">
          <Link to="/profile/friendsearch">
            <SearchIcon width={20} height={20} />
          </Link>
        </div>
      </div>

      {friends &&
        friends.map((friend, index) => (
          <div className="mt-6" key={index}>
            <Friend
              campusSection={friend.campusSection}
              name={friend.name}
              userId={friend.userId}
              profileImage={friend.profileImage}
              follow={friend.follow}
              sameCampus={friend.sameCampus}
              cohort={friend.cohort}
            />
          </div>
        ))}
    </div>
  );
};

export default FriendList;
