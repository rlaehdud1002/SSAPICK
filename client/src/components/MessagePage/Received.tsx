import { useEffect, useRef, useCallback } from 'react';
import { useInfiniteQuery } from '@tanstack/react-query';
import { getReceivedMessage } from 'api/messageApi';
import { IMessage } from 'atoms/Message.type';
import { IPaging } from 'atoms/Pick.type';
import MessageContent from 'components/MessagePage/MessageContent';
import NoMessage from 'components/MessagePage/NoMessage';
import Loading from 'components/common/Loading';

const Received = () => {
  const {
    data,
    isLoading,
    isError,
    fetchNextPage,
    hasNextPage,
    isFetchingNextPage,
  } = useInfiniteQuery<IPaging<IMessage[]>>({
    queryKey: ['message', 'received'],
    queryFn: ({ pageParam = 0 }) => getReceivedMessage(pageParam as number, 10),
    getNextPageParam: (lastPage, pages) => {
      if (!lastPage.last) {
        return pages.length;
      }
      return undefined;
    },
    initialPageParam: 0,
  });

  const observerElem = useRef<HTMLDivElement>(null);
  const scrollPosition = useRef(0);

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

  if (isLoading) {
    return <Loading />;
  }

  if (isError) {
    return <div>에러</div>;
  }

  return (
    <div>
      {data?.pages.flatMap((page) => page.content).length ? (
        data.pages
          .flatMap((page) => page.content)
          .map((message, index) => (
            <MessageContent key={index} message={message} status="receive" />
          ))
      ) : (
        <NoMessage content="받은 쪽지가 없습니다." />
      )}
      <div ref={observerElem} />
      {isFetchingNextPage && <div>Loading more messages...</div>}
    </div>
  );
};

export default Received;
