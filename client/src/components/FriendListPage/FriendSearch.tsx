import { useInfiniteQuery, useQuery } from '@tanstack/react-query';
import { getRecommendFriendsList, getSearchFriendsList } from 'api/friendApi';
import { IContent, ISearchData, ISearchFriend } from 'atoms/Friend.type';
import { Input } from 'components/ui/input';
import { Separator } from 'components/ui/separator';
import BackIcon from 'icons/BackIcon';
import FriendIcon from 'icons/FriendIcon';
import FriendPlusIcon from 'icons/FriendPlusIcon';
import ShuffleIcon from 'icons/ShuffleIcon';
import { useCallback, useEffect, useRef } from 'react';
import { useForm } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';
import FriendRecommendContent from './FriendRecommendContent';
import FriendSearchContent from './FriendSearchContent';

interface FriendSearchForm {
  search: string;
}

const FriendSearch = () => {
  const {
    register,
    handleSubmit,
    reset,
    watch,
    formState: { isSubmitSuccessful },
  } = useForm<FriendSearchForm>();

  const navigate = useNavigate();

  const onSubmit = (data: FriendSearchForm) => {
  }

  // 추천 친구 목록 조회
  const { data: recommendFriends, isLoading: LoadingRecommendFriends, isError, hasNextPage, fetchNextPage } =
    useInfiniteQuery<ISearchData<IContent[]>>({
      queryKey: ['recommendFriends'],
      queryFn: ({ pageParam = 0 }) => getRecommendFriendsList(pageParam as number, 10),
      getNextPageParam: (lastPage, pages) => {
        if (!lastPage.last) {
          return pages.length;
        }
        return undefined;
      },
      initialPageParam: 0,
    });

  console.log(recommendFriends?.pages[0].content);

  // 검색 친구 리스트 조회
  const { data: searchFriend, isLoading, refetch } = useQuery<ISearchFriend>({
    queryKey: ['searchFriends', watch("search")],
    queryFn: () => getSearchFriendsList(watch("search"))
  });

  console.log("friend", searchFriend)

  useEffect(() => {
    if (isSubmitSuccessful) {
      reset();
    }
  }, [isSubmitSuccessful, reset]);

  const scrollPosition = useRef(0);
 
  const handleClick = () => {
    if (hasNextPage){
      fetchNextPage();
    }
  }

  return (
    <div className="relative flex flex-col">
      <div className=" flex ml-2">
        <div onClick={() => navigate(-1)} className="mr-2">
          <BackIcon />
        </div>
        <FriendPlusIcon width={20} height={20} />
        <div className="ml-2">추천친구</div>
        <div onClick={handleClick} className="flex items-center mx-1">
          <ShuffleIcon className="cursor-pointer" />
        </div>
      </div>
      <div className="flex flex-col">
        <div className='flex'>
        {recommendFriends?.pages.length ? (recommendFriends.pages.map((friend, index) => (
          friend.content.map((friendcontent, index) => (
            <div>
              <FriendRecommendContent cohort={friendcontent.cohort} classNum={friendcontent.campusSection} name={friendcontent.name} userId={friendcontent.userId} profileImage={friendcontent.profileImage} />
            </div>
          ))
        ))) : (
          <span className='text-xs ml-36 mt-3'>추천하는 친구가 없습니다.</span>
        )}
        </div>
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
              <Input
                className="w-full bg-white h-10"
                type="text"
                placeholder="친구 검색"
                register={register('search')}
              />
            </div>
          </div>
        </form>
      </div>
      {searchFriend?.content.length ? (
        searchFriend.content.map((friend, index) => (
          <FriendSearchContent key={index} userId={friend.userId} follow={friend.follow} profileImage={friend.profileImage} cohort={friend.cohort} classSection={friend.campusSection} name={friend.name} />
        ))
      ) : (
        <div className="flex justify-center">
          <span className="text-xs mt-3"> 검색한 친구가 없습니다</span>
        </div>
      )}
    </div>
  );
};

export default FriendSearch;
