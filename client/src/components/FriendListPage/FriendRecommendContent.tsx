import { useMutation, useQuery } from '@tanstack/react-query';
import { deleteFriend, getRecommendFriendsList, postAddFriend } from 'api/friendApi';
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
          {recommendFriends.length? (recommendFriends.map((friend, index) => (
            <ToPlusIcon key={index} classNum={friend.campusSection} name={friend.name} profileImage={friend.profileImage} userId={friend.userId}/>
          ))):(
            <span className='text-xs ml-36 mt-3'>추천하는 친구가 없습니다.</span>
          )}  
        </div>
      </div>
    </div>
  );
};
export default FriendRecommendContent;
