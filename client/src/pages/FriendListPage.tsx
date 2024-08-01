import { friendListState } from "atoms/FriendAtoms";
import Friend from "components/FriendListPage/FriendBox";
import BackIcon from "icons/BackIcon";
import FriendIcon from "icons/FriendIcon";
import SearchIcon from "icons/SearchIcon";
import { Link, useNavigate } from "react-router-dom";
import { useRecoilValue } from "recoil";

import { QueryClient } from '@tanstack/react-query';

const FriendList = () => {

  const queryClient = new QueryClient();

  const navigate = useNavigate();


  const friendList = useRecoilValue(friendListState);
  console.log(friendList)

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
      {friendList.map((friend) => (
        <div className="mt-6">
          <Friend key={friend.userId} campus={friend.campusName} campusSection={friend.campusSection} campusDescription={friend.campusDescription} name={friend.nickname} />
        </div>
      ))}
    </div>
  )
}

export default FriendList;