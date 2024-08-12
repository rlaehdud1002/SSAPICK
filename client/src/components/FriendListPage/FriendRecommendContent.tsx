import { useInfiniteQuery } from '@tanstack/react-query';
import {
  getRecommendFriendsList
} from 'api/friendApi';
import { IContent, ISearchData } from 'atoms/Friend.type';
import Loading from 'components/common/Loading';
import { useCallback, useRef } from 'react';

const FriendRecommendContent = () => {
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
  const scrollPosition = useRef(0);
  const handleObserver = useCallback(
    (entries: IntersectionObserverEntry[]) => {
      const [target] = entries;
      if (target && hasNextPage) {
        scrollPosition.current = window.scrollX;
        fetchNextPage();
      }
    },
    [fetchNextPage, hasNextPage]
  );

  if (LoadingRecommendFriends) {
    return <Loading />;
  }

  return (
    <div className="w-full">
      <div>
        <div className="flex overflow-x-scroll scrollbar-hide">
          {/* {recommendFriends?.pages.length? (recommendFriends.pages.map((friend, index) => (
            <ToPlusIcon key={index} cohort={friend.content.cohort} classNum={friend.campusSection} name={friend.name} profileImage={friend.profileImage} userId={friend.userId}/>
          ))):(
            <span className='text-xs ml-36 mt-3'>추천하는 친구가 없습니다.</span>
          )}   */}
        </div>
      </div>
    </div>
  );
};
export default FriendRecommendContent;
