import { useMutation, useQuery } from '@tanstack/react-query';
import {
  deleteFriend,
  getRecommendFriendsList,
  postAddFriend,
} from 'api/friendApi';
import { IContent, IFriend, ISearchData } from 'atoms/Friend.type';
import Loading from 'components/common/Loading';
import ProfileIcon from 'icons/ProfileIcon';
import ToPlusIcon from 'icons/ToPlusIcon';

const FriendRecommendContent = () => {
  // 추천 친구 목록 조회
  const { data: recommendFriends = {content:[] as IContent[]}, isLoading: LoadingRecommendFriends } =
    useQuery<ISearchData<IContent[]>>({
      queryKey: ['recommendFriends'],
      queryFn: getRecommendFriendsList,
    });
  console.log(recommendFriends.content);

  if (LoadingRecommendFriends) {
    return <Loading />;
  }

  return (
    <div className="w-full">
      <div>
        <div className="flex overflow-x-scroll scrollbar-hide">
          {recommendFriends.content.length? (recommendFriends.content.map((friend, index) => (
            <ToPlusIcon key={index} cohort={friend.cohort} classNum={friend.campusSection} name={friend.name} profileImage={friend.profileImage} userId={friend.userId}/>
          ))):(
            <span className='text-xs ml-36 mt-3'>추천하는 친구가 없습니다.</span>
          )}  
        </div>
      </div>
    </div>
  );
};
export default FriendRecommendContent;
