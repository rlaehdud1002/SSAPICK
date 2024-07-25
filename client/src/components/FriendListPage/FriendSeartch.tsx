import BackIcon from "icons/BackIcon";
import FriendPlusIcon from "icons/FriendPlusIcon";
import { useNavigate } from "react-router-dom";
import FriendSearchContent from "./FriendSearchContent";
import { Separator } from "components/ui/separator";
import ShuffleIcon from "icons/ShuffleIcon";

const FriendSearch = () => {
  const navigate = useNavigate();

  return (
  <div className="flex flex-col">
    <div className="flex ml-2">
      <div onClick={() => navigate(-1)} className="mr-2">
        <BackIcon />
      </div>
      <FriendPlusIcon width={20} height={20} />
      <div className="ml-2">추천친구</div>
      <div className="flex items-center mx-1">
      <ShuffleIcon className="cursor-pointer" />
      </div>
    </div>
      <div className="flex flex-col">
      <FriendSearchContent/>
      <div className="mx-5">
      <Separator className="my-4" />
      </div>

      </div>
  </div>)
}

export default FriendSearch; 