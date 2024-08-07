import Response from "components/MainPage/Response";
import Initial from "components/MainPage/Initial";

import { useMutation, useQuery } from "@tanstack/react-query";
import { getReceivePick } from "api/pickApi";
import { IPick } from "atoms/Pick.type";
import { useEffect, useState } from "react";
import { getAttendance, postAttendance } from "api/attendanceApi";
import AttendanceModal from "components/modals/AttendanceModal";

const Home = () => {
  const { data: picks, isLoading } = useQuery<IPick[]>({
    queryKey: ["pick", "receive"],
    queryFn: getReceivePick,
  });

  console.log("picks", picks);

  const [modalOpen, setModalOpen] = useState(false);
  const [streak, setStreak] = useState(0);

  // 이건 들어오자마자 실행
  const { data: attendance, isLoading: isLoadingAttendance } = useQuery({
    queryKey: ["getattendance"],
    queryFn: getAttendance,
  });

  const [isAttendance, setIsAttendance] = useState(attendance?.todayChecked);

  const postMutation = useMutation({
    mutationKey: ["postAttendance"],
    mutationFn: postAttendance,

    onSuccess: (data) => {
      console.log("postsuccess", data);
      setIsAttendance(data.todayChecked);
      setStreak(data.streak);
      setModalOpen(true);
    },

    onError: (error) => {
      console.log("이미 출석체크 완료");
    },
  });

  // 출석 체크
  // useEffect(() => {
  //   console.log('!isAttendance', !isAttendance);
  //   console.log('!isLoadingAttendance', !isLoadingAttendance);

  //   if (!isAttendance && !isLoadingAttendance) {
  //     console.log('출석체크 요청');
  //     postMutation.mutate();
  //   }
  // }, [isAttendance, isLoadingAttendance]);

  return (
    <div className="m-6">
      {picks !== undefined && picks.length !== 0 ? (
        <Response picks={picks} isLoading={isLoading} />
      ) : (
        <Initial />
      )}
      {modalOpen && <AttendanceModal date={streak} onClose={() => setModalOpen(false)} />}
    </div>
  );
};

export default Home;
