import { useQuery } from '@tanstack/react-query';
import { getRecommendFriendsList } from 'api/friendApi';
import { IFriend } from 'atoms/Friend.type';
import ProfileIcon from 'icons/ProfileIcon';
import ToPlusIcon from 'icons/ToPlusIcon';

const FriendRecommendContent = () => {
  // 추천 친구 목록 조회
  const { data: recommendFriends = [], isLoading: LoadingRecommendFriends } = useQuery<IFriend[]>({
    queryKey: ['recommendFriends'],
    queryFn: async () => await getRecommendFriendsList(),
  });
  console.log(recommendFriends)
  return (
    <div className="w-full">
      <div>
        <div className="flex overflow-x-scroll scrollbar-hide">
          {recommendFriends?.map((friend, index) => (
            <ToPlusIcon key={index} campus={friend.campusName} classNum={friend.campusSection} name={friend.nickname} profileImage={friend.profileImage}/>
          ))}  

          {/* {[0, 1, 2, 3, 4, 5, 6, 7, 8].map((item, index) => {
            return (
              <ToPlusIcon
                key={index}
                campus="광주"
                th={11}
                classNum={2}
                name="민준수"
              />
            );
          })} */}
          {/* <ToPlusIcon campus="광주" th={11} classNum={2} name="민준수" isPlus={true} /> */}
        </div>
      </div>
    </div>
  );
};
export default FriendRecommendContent;
