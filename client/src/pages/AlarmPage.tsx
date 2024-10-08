import AlarmIcon from 'icons/AlarmIcon';
import BackIcon from 'icons/BackIcon';
import AlarmContent from 'components/AlarmPage/AlarmContent';

import { useNavigate } from 'react-router-dom';
import { useInfiniteQuery } from '@tanstack/react-query';
import { INotification } from 'atoms/Notification.type';
import { getNotificationList, readNotification } from 'api/notificationApi';
import { IPaging } from 'atoms/Pick.type';
import { useCallback, useEffect, useRef } from 'react';
import Loading from 'components/common/Loading';
import { newAlarmState } from 'atoms/AlarmAtoms';
import { useSetRecoilState } from 'recoil';

const Alarm = () => {
  const nav = useNavigate();
  const setNewAlarm = useSetRecoilState(newAlarmState);

  useEffect(() => {
    setNewAlarm(false);
  }, [setNewAlarm])

  const { data, isLoading, fetchNextPage, hasNextPage, isFetchingNextPage } =
    useInfiniteQuery<IPaging<INotification[]>>({
      queryKey: ['notification'],
      queryFn: ({ pageParam = 0 }) =>
        getNotificationList(pageParam as number, 10),
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
    [fetchNextPage, hasNextPage],
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
    if (!isFetchingNextPage) {
      window.scrollTo(0, scrollPosition.current);
    }
  }, [isFetchingNextPage]);

  const handleBackClick = async () => {
    try {
      await readNotification(); // 알림 읽음 처리 API 호출
      nav(-1); // 뒤로가기
    } catch (error) {
      console.error('알림 읽음 처리 중 오류 발생:', error);
      // 필요시 사용자에게 에러 메시지 표시
    }
  };

  if (isLoading) return <Loading />;

  return (
    <div>
      <div
        className="flex flex-row items-center m-2 cursor-pointer"
        onClick={handleBackClick}
      >
        <BackIcon />
        <AlarmIcon />
      </div>
      <div className="m-6">
        {data?.pages.flatMap((page) => page.content).length ? (
          <>
            {data.pages.flatMap((page, pageIndex) =>
              page.content.map((notification, index) => (
                <AlarmContent
                  notification={notification}
                  key={`${pageIndex}-${index}`}
                />
              )),
            )}
            <div ref={observerElem} className="observer-element">
              {isFetchingNextPage && <Loading />}
            </div>
          </>
        ) : (
          <div className="text-center ">받은 알림이 없습니다.</div>
        )}
      </div>
    </div>
  );
};

export default Alarm;
