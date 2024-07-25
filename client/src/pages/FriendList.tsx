import Friend from "components/FriendListPage/Friend";
import BackIcon from "icons/BackIcon";
import FriendIcon from "icons/FriendIcon";
import SearchIcon from "icons/SearchIcon";
import { Link, useNavigate } from "react-router-dom";


const FriendList = () => {
  const navigate = useNavigate();

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
          <Link to="/FriendSearch">
            <SearchIcon width={20} height={20} />
          </Link>
        </div>
      </div>
      <div className="mt-6">
        <Friend campus="광주" th={11} classNum={2} name="민준수" />
      </div>
    </div>
  )
}

export default FriendList;