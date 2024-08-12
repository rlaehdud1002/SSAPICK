import { useInfiniteQuery } from '@tanstack/react-query';
import { getPickcolog } from 'api/pickcologApi';
import { IPaging } from 'atoms/Pick.type';
import { IPickcolog } from 'atoms/Pickcolog.type';
import Loading from 'components/common/Loading';
import PickcoLogList from 'components/PickcologPage/PickcoLogList';
import BackIcon from 'icons/BackIcon';
import { useCallback, useEffect, useRef } from 'react';
import { useNavigate } from 'react-router-dom';

const Pickcolog = () => {
  const nav = useNavigate();
  const observerElem = useRef<HTMLDivElement>(null);
  const scrollPosition = useRef(0);

  const {
    data,
    isLoading,
    isError,
    fetchNextPage,
    hasNextPage,
    isFetchingNextPage,
  } = useInfiniteQuery<IPaging<IPickcolog[]>>({
    queryKey: ['pickcolog'],
    queryFn: ({ pageParam = 0 }) => getPickcolog(pageParam as number, 10),
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
      if (target.isIntersecting && hasNextPage) {
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

  useEffect(() => {
    if (!isFetchingNextPage) {
      window.scrollTo(0, scrollPosition.current);
    }
  }, [isFetchingNextPage]);

  useEffect(() => {
    if (data && !hasNextPage) {
      console.log('조회가 완료되었습니다.');
    }
  }, [data, hasNextPage]);

  if (isLoading) {
    return <Loading />;
  }

  if (isError) {
    return <div>에러 발생...</div>;
  }

  return (
    <div className="m-5">
      <div className="flex flex-row items-center mb-4" onClick={() => nav(-1)}>
        <BackIcon />
        <span>뒤로 가기</span>
      </div>
      <div className="m-6">
        {data?.pages.flatMap((page) => page.content).length ? (
          <div>
            {data.pages
              .flatMap((page) => page.content)
              .map((item) => (
                <PickcoLogList key={item.id} pickcolog={item} />
              ))}
          </div>
        ) : (
          <div>
            <span className="luckiest_guy">PICKCO</span>를 사용한 내역이
            없습니다.
          </div>
        )}
        <div ref={observerElem} />
        {isFetchingNextPage && <div>로딩 중...</div>}
      </div>
    </div>
  );
};

export default Pickcolog;
