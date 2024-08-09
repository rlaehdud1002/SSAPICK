import AlarmIcon from "icons/AlarmIcon";
import BackIcon from "icons/BackIcon";
import AlarmContent from "components/AlarmPage/AlarmContent";

import { useNavigate } from "react-router-dom";
import { useInfiniteQuery, useQuery } from "@tanstack/react-query";
import { INotification } from "atoms/Notification.type";
import { getNotificationList } from "api/notificationApi";
import { IPaging } from "atoms/Pick.type";
import { useCallback, useEffect, useRef } from "react";

const Alarm = () => {
  const nav = useNavigate();

  const { data, isLoading, isError, fetchNextPage, hasNextPage, isFetchingNextPage } =
    useInfiniteQuery<IPaging<INotification[]>>({
      queryKey: ["pick", "receive"],
      queryFn: ({ pageParam = 0 }) => getNotificationList(pageParam as number, 10),
      getNextPageParam: (lastPage, pages) => {
        if (!lastPage.last) {
          return pages.length;
        }
        return undefined;
      },
      initialPageParam: 0,
    });

  const handleObserver = useCallback(
    (entries: IntersectionObserverEntry[]) => {
      const [target] = entries;
      if (target && hasNextPage) {
        scrollPosition.current = window.scrollY;
        fetchNextPage();
      }
    },
    [fetchNextPage, hasNextPage]
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

  const observerElem = useRef<HTMLDivElement>(null);
  const scrollPosition = useRef(0);

  useEffect(() => {
    if (data && !hasNextPage) {
      console.log("조회가 완료되었습니다.");
    }
  }, [data, hasNextPage]);

  useEffect(() => {
    if (!isFetchingNextPage) {
      window.scrollTo(0, scrollPosition.current);
    }
  }, [isFetchingNextPage]);

  if (isLoading) return <div>로딩중</div>;

  return (
    <div>
      <div className="flex flex-row items-center m-2 cursor-pointer" onClick={() => nav(-1)}>
        <BackIcon />
        <AlarmIcon />
        {/* <h1>알림</h1> */}
      </div>
      <div className="m-6">
        {data?.pages.flatMap((page) => page.content).length ? (
          <>
            {data.pages.flatMap((page) =>
              page.content.map((notification) => (
                <AlarmContent
                  category={notification.type}
                  content={notification.message}
                  read={notification.read}
                />
              ))
            )}
            <div ref={observerElem} className="observer-element">
              {isFetchingNextPage && <div>로딩중...</div>}
            </div>
          </>
        ) : (
          <div>알림이 없습니다.</div>
        )}
      </div>
    </div>
  );
};

export default Alarm;
