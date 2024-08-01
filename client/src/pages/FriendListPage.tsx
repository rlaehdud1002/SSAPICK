import { QueryClient, useMutation, useQuery } from '@tanstack/react-query';
import { getFriendsList } from 'api/friendApi';
import { IFriend } from 'atoms/Friend.type';
import Friend from 'components/FriendListPage/FriendBox';
import BackIcon from 'icons/BackIcon';
import FriendIcon from 'icons/FriendIcon';
import SearchIcon from 'icons/SearchIcon';
import { Link, useNavigate } from 'react-router-dom';

const FriendList = () => {
  const queryClient = new QueryClient();
  const { data: friends, isLoading } = useQuery<IFriend[]>({
    queryKey: ['friends'],
    queryFn: async () => await getFriendsList(),
  });

  const navigate = useNavigate();

  console.log(friends, isLoading);

  return (
    <div>
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
        friends.map((friend) => (
          <div className="mt-6">
            <Friend
              key={friend.userId}
              campus={friend.campusName}
              campusSection={friend.campusSection}
              campusDescription={friend.campusDescription}
              name={friend.nickname}
            />
          </div>
        ))}
    </div>
  );
};

export default FriendList;
