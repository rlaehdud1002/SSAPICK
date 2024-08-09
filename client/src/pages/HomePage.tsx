import { useEffect, useState, useRef, useCallback } from "react";
import { useInfiniteQuery, useMutation, useQuery } from "@tanstack/react-query";
import { getReceivePick } from "api/pickApi";
import { IPaging, IPick } from "atoms/Pick.type";
import Response from "components/MainPage/Response";
import Initial from "components/MainPage/Initial";
import AttendanceModal from "components/modals/AttendanceModal";
import { getAttendance, postAttendance } from "api/attendanceApi";
import { IUserAttendance } from "atoms/User.type";
import { userAttendanceState } from "atoms/UserAtoms";
import { useRecoilState } from "recoil";

const Home = () => {
  const { data, isLoading, isError, fetchNextPage, hasNextPage, isFetchingNextPage } =
    useInfiniteQuery<IPaging<IPick[]>>({
      queryKey: ["pick", "receive"],
      queryFn: ({ pageParam = 0 }) => getReceivePick(pageParam as number, 10),
      getNextPageParam: (lastPage, pages) => {
        if (!lastPage.last) {
          return pages.length;
        }
        return undefined;
      },
      initialPageParam: 0,
    });

  const [modalOpen, setModalOpen] = useState(false);
  const [hasCheckedAttendance, setHasCheckedAttendance] = useState(false);
  const observerElem = useRef<HTMLDivElement>(null);
  const scrollPosition = useRef(0);

  const [attendanceStatus, setAttendanceStatus] = useRecoilState(userAttendanceState);

  const { data: attendance } = useQuery<IUserAttendance>({
    queryKey: ["getattendance"],
    queryFn: getAttendance,
    enabled: !attendanceStatus.todayChecked,
  });

  const postMutation = useMutation({
    mutationKey: ["postAttendance"],
    mutationFn: postAttendance,
    onSuccess: (data) => {
      setAttendanceStatus({
        todayChecked: data.todayChecked,
        streak: data.streak,
      });
      setModalOpen(true);
    },
    onError: () => {
      console.log("이미 출석체크 완료");
    },
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

  useEffect(() => {
    console.log("asd : ", attendanceStatus.todayChecked, attendance, "asd: ", hasCheckedAttendance);
    if (!attendanceStatus.todayChecked && attendance && !hasCheckedAttendance) {
      postMutation.mutate();
      setHasCheckedAttendance(true);
    }
  }, [attendance, attendanceStatus.todayChecked, hasCheckedAttendance, postMutation]);

  if (isError) return <div>에러 발생...</div>;

  return (
    <div className="m-6">
      {data?.pages.flatMap((page) => page.content).length ? (
        <Response
          picks={data.pages.flatMap((page) => page.content)}
          isLoading={isLoading || isFetchingNextPage}
        />
      ) : (
        <Initial />
      )}
      <div ref={observerElem} />
      {isFetchingNextPage && <div>로딩 중...</div>}
      {modalOpen && (
        <AttendanceModal date={attendanceStatus.streak} onClose={() => setModalOpen(false)} />
      )}
    </div>
  );
};

export default Home;
