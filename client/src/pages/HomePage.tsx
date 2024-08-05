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

  console.log("attendance", isAttendance);

  // 이건 postMutation의 onSuccess에서 실행
  const getMutation = useMutation({
    mutationKey: ["getAttendance"],
    mutationFn: getAttendance,

    onSuccess: (data) => {
      console.log("출석체크 성공");
      console.log(data);
      // 그럼 여기서 todayChecked를 바꿔주고
      // streak도 바꿔주고
      setIsAttendance(data.todayChecked);
      setStreak(data.streak);
      // 여기서 모달 열면 되겠다
      setModalOpen(true);
    },
  });

  const postMutation = useMutation({
    mutationKey: ["postAttendance"],
    mutationFn: postAttendance,

    onSuccess: (data) => {
      console.log("postsuccess");
      console.log(data);
      getMutation.mutate();
    },

    onError: (error) => {
      console.log("이미 출석체크 완료");
    },
  });

  // 출석 체크
  // useEffect(() => {
  //   console.log('attendance', 'useEffect', isAttendance);
  //   if (!isAttendance) {
  //     postMutation.mutate();
  //   }
  // }, [isAttendance]);

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
