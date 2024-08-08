import { Separator } from "components/ui/separator";
import BackIcon from "icons/BackIcon";
import FriendIcon from "icons/FriendIcon";
import FriendPlusIcon from "icons/FriendPlusIcon";
import ShuffleIcon from "icons/ShuffleIcon";
import { useNavigate } from "react-router-dom";
import FriendRecommendContent from "./FriendRecommendContent";
import FriendSearchContent from "./FriendSearchContent";
import Search from "./SearchBox";

const FriendSearch = () => {
  const navigate = useNavigate();
  console.log()
  return (

    <div className="relative flex flex-col">
        <div className=" flex ml-2">
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
          <FriendRecommendContent />
          <div className="mx-5">
            <Separator className="my-4" />
          </div>

          <div className="flex ml-8 mb-3">
            <FriendIcon width={20} height={20} isDefault={true} />
            <span className="ml-2">친구찾기</span>
          </div>
          
          <Search />
        </div>
        {/* </div> */}
        {[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15].map((index) => (
          <FriendSearchContent key={index} campus="광주" th={11} classNum={2} name="민준수" />
        ))}

    </div>
  )
}

export default FriendSearch; 