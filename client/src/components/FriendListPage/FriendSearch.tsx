import { useQuery } from "@tanstack/react-query";
import { getSearchFriendsList } from "api/friendApi";
import { IFriend } from "atoms/Friend.type";
import { Input } from "components/ui/input";
import { Separator } from "components/ui/separator";
import BackIcon from "icons/BackIcon";
import FriendIcon from "icons/FriendIcon";
import FriendPlusIcon from "icons/FriendPlusIcon";
import ShuffleIcon from "icons/ShuffleIcon";
import { useEffect } from "react";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import FriendRecommendContent from "./FriendRecommendContent";
import FriendSearchContent from "./FriendSearchContent";

interface FriendSearchForm {
  search: string;
}

const FriendSearch = () => {

  const { register, handleSubmit, reset, formState: { isSubmitSuccessful } } = useForm<FriendSearchForm>();

  const navigate = useNavigate();

  const onSubmit = (data: FriendSearchForm) => {
    console.log(data)
  }

  // 검색 친구 리스트 조회
  const { data: searchFriend, isLoading } = useQuery<IFriend[]>({
    queryKey: ['searchFriends'],
    queryFn: async () => await getSearchFriendsList(""),

  });
  console.log("friend",searchFriend)

  useEffect(() => {
    if (isSubmitSuccessful) {
      reset()
    }

  }, [isSubmitSuccessful, reset]);

  
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
        <form onSubmit={handleSubmit(onSubmit)}>
          <div className="flex relative items-center space-x-2 m-auto">
            <div className="w-4/5 flex flex-row m-auto">
              <Input className="w-full bg-white h-10" type="text" placeholder="친구 검색" register={register("search")} />
            </div>
          </div>
        </form>
      </div>

      {/* <div className="flex flex-col"> */}
      {/* <FriendSearchContent campus="광주" th={11} classNum={2} name="민준수" /> */}
      {/* <FriendSearchContent campus="광주" th={11} classNum={2} name="이호영" /> */}
      {/* </div> */}
      {/* <div className="flex ml-8 mb-3">
        <FriendIcon width={20} height={20} isDefault={true} />
        <span className="ml-2">친구찾기</span>


      {/* </div> */}
      {[0, 1].map((index) => (
        <FriendSearchContent key={index} campus="광주" th={11} classNum={2} name="민준수" />
      ))}
      {[0, 1, 2, 3, 4, 5].map((index) => (
        <FriendSearchContent key={index} campus="광주" th={11} classNum={2} name="이호영" />
      ))}

    </div>
  )
}

export default FriendSearch; 