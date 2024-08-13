import { useInfiniteQuery, useQueryClient } from "@tanstack/react-query";
import { getRecommendFriendsList, getSearchFriendsList } from "api/friendApi";
import { IContent } from "atoms/Friend.type";
import { Input } from "components/ui/input";
import { Separator } from "components/ui/separator";
import BackIcon from "icons/BackIcon";
import FriendIcon from "icons/FriendIcon";
import FriendPlusIcon from "icons/FriendPlusIcon";
import ShuffleIcon from "icons/ShuffleIcon";
import { useCallback, useEffect, useRef, useState, useTransition } from "react";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import FriendRecommendContent from "./FriendRecommendContent";
import FriendSearchContent from "./FriendSearchContent";
import { IPaging } from "atoms/Pick.type";
import Loading from "components/common/Loading";

interface FriendSearchForm {
  search: string;
}

const MIN_WAIT_TIME = 500;

const FriendSearch = () => {
  const queryClient = useQueryClient();

  const [isPending, startTransition] = useTransition();
  const [showLoading, setShowLoading] = useState(false);

  const {
    register,
    handleSubmit,
    reset,
    watch,
    formState: { isSubmitSuccessful },
  } = useForm<FriendSearchForm>();

  const navigate = useNavigate();

  const onSubmit = (data: FriendSearchForm) => {};

  const [recommendFriendstate, setRecommendFriendstate] = useState<IContent[] | null>(null);

  // 추천 친구 목록 조회
  const {
    data: recommendFriends,
    isLoading: LoadingRecommendFriends,
    isError,
    fetchNextPage,
    hasNextPage,
    isFetchingNextPage,
    refetch,
  } = useInfiniteQuery<IPaging<IContent[]>>({
    queryKey: ["recommendFriends"],
    queryFn: ({ pageParam = 0 }) => getRecommendFriendsList(pageParam as number, 4),
    getNextPageParam: (lastPage, pages) => {
      if (!lastPage.last) {
        return pages.length;
      }
      return undefined;
    },
    initialPageParam: 0,
  });

  useEffect(() => {
    if (recommendFriends?.pages.length) {
      const latestPage = recommendFriends.pages[recommendFriends.pages.length - 1];
      setRecommendFriendstate(latestPage.content);
    }
  }, [recommendFriends]);

  const search = watch("search");

  const {
    data: searchFriend,
    isLoading: LoadingSearchFriends,
    isError: isErrorSearchFriends,
    fetchNextPage: fetchNextPageSearch,
    hasNextPage: hasNextPageSearch,
    isFetchingNextPage: isFetchingNextPageSearch,
    refetch: refetchSearch,
  } = useInfiniteQuery<IPaging<IContent[]>>({
    queryKey: ["searchFriends", watch("search")],
    queryFn: ({ pageParam = 0 }) => getSearchFriendsList(pageParam as number, 5, search),
    getNextPageParam: (lastPage, pages) => {
      if (!lastPage.last) {
        return pages.length;
      }
      return undefined;
    },
    initialPageParam: 0,
  });

  useEffect(() => {
    if (isSubmitSuccessful) {
      reset();
    }
  }, [isSubmitSuccessful, reset]);

  const observerElem = useRef<HTMLDivElement>(null);
  const scrollPosition = useRef(0);

  useEffect(() => {
    if (!isFetchingNextPageSearch) {
      window.scrollTo(0, scrollPosition.current);
    }
  }, [isFetchingNextPageSearch]);

  const handleObserver = useCallback(
    (entries: IntersectionObserverEntry[]) => {
      const [target] = entries;
      if (target && hasNextPageSearch) {
        scrollPosition.current = window.scrollY;
        fetchNextPageSearch();
      }
    },
    [fetchNextPageSearch, hasNextPageSearch]
  );

  useEffect(() => {
    const element = observerElem.current;
    const option = { threshold: 1.0 };
    const observer = new IntersectionObserver(handleObserver, option);
    if (element) observer.observe(element);
    return () => {
      if (element) observer.unobserve(element);
    };
  }, [handleObserver]);

  const handleClick = () => {
    startTransition(() => {
      setShowLoading(true);
      setTimeout(() => {
        if (hasNextPage) {
          fetchNextPage();
        } else {
          queryClient.removeQueries({ queryKey: ["recommendFriends"] });
          queryClient.invalidateQueries({ queryKey: ["recommendFriends"] });
          refetch();
        }
        setShowLoading(false);
      }, MIN_WAIT_TIME);
    });
  };

  useEffect(() => {
    if (searchFriend && !hasNextPageSearch) {
      console.log("조회가 완료되었습니다.");
    }
  }, [searchFriend, hasNextPageSearch]);

  const shouldShowLoading =
    showLoading &&
    recommendFriends?.pages.length &&
    recommendFriends.pages.flatMap((page) => page.content).length > 0;

  return (
    <div
      className={`relative flex flex-col ${isPending || showLoading ? "pointer-events-none" : ""}`}
    >
      <div className="flex ml-2">
        <div onClick={() => navigate(-1)} className="mr-2">
          <BackIcon />
        </div>
        <FriendPlusIcon width={20} height={20} />
        <div className="ml-2">추천친구</div>
        <div onClick={handleClick} className="flex items-center mx-1">
          <ShuffleIcon className="cursor-pointer" />
        </div>
      </div>
      <div className="flex flex-col relative">
        <div className={`flex ${isPending || showLoading ? "relative dimmed" : ""}`}>
          {recommendFriendstate?.length ? (
            recommendFriendstate.flatMap((recommendFriend) => (
              <FriendRecommendContent
                key={recommendFriend.userId}
                cohort={recommendFriend.cohort}
                classNum={recommendFriend.campusSection}
                name={recommendFriend.name}
                userId={recommendFriend.userId}
                profileImage={recommendFriend.profileImage}
              />
            ))
          ) : (
            <span className="text-xs ml-36 mt-3">추천하는 친구가 없습니다.</span>
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
                register={register("search")}
              />
            </div>
          </div>
        </form>
      </div>
      {searchFriend?.pages.flatMap((page) => page.content).length ? (
        searchFriend.pages
          .flatMap((page) => page.content)
          .map((content) => (
            <FriendSearchContent
              key={content.userId}
              cohort={content.cohort}
              classSection={content.campusSection}
              name={content.name}
              userId={content.userId}
              profileImage={content.profileImage}
            />
          ))
      ) : (
        <div className="flex justify-center">
          <span className="text-xs mt-3"> 검색한 친구가 없습니다</span>
        </div>
      )}
      {shouldShowLoading && (
        <div className="fixed inset-0 bg-gray-800 bg-opacity-50 flex items-center justify-center z-50">
          <div className="text-white py-2 px-4 rounded shadow-lg">
            <Loading />
            <div className="mt-2">아주 복잡한 알고리즘으로 추천친구를 불러오는 중...</div>
          </div>
        </div>
      )}
      <div ref={observerElem} />
      {isFetchingNextPageSearch && <Loading />}
      {searchFriend && !hasNextPageSearch && (
        <span className="flex justify-center text-sm my-10">조회가 완료되었습니다.</span>
      )}
    </div>
  );
};

export default FriendSearch;
