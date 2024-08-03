import { useRecoilState, useRecoilValue } from 'recoil';
import AlarmIcon from '../../icons/AlarmIcon';
import CoinIcon from '../../icons/CoinIcon';
import Logo from '../../icons/Logo';
import AttendanceModal from 'components/modals/AttendanceModal';

import { userAttendanceState, userCoinState } from 'atoms/UserAtoms';
import { Link } from 'react-router-dom';
import { useMutation, useQuery } from '@tanstack/react-query';
import { getAttendance, postAttendance } from 'api/attendanceApi';
import { useEffect, useState } from 'react';

const Header = () => {
  const coin = useRecoilValue(userCoinState);
  const [modalOpen, setModalOpen] = useState(false);
  const [streak, setStreak] = useState(0);
  // console.log('isAttendance', isAttendance);

  // 이건 들어오자마자 실행
  const { data: attendance, isLoading: isLoadingAttendance } = useQuery({
    queryKey: ['getattendance'],
    queryFn: getAttendance,
  });

  const [isAttendance, setIsAttendance] = useState(attendance?.todayChecked);

  console.log('attendance', isAttendance);

  // 이건 postMutation의 onSuccess에서 실행
  const getMutation = useMutation({
    mutationKey: ['getAttendance'],
    mutationFn: getAttendance,

    onSuccess: (data) => {
      console.log('출석체크 성공');
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
    mutationKey: ['postAttendance'],
    mutationFn: postAttendance,

    onSuccess: (data) => {
      console.log('postsuccess');
      console.log(data);
      getMutation.mutate();
    },

    onError: (error) => {
      console.log('이미 출석체크 완료');
    },
  });

  // 출석 체크
  useEffect(() => {
    console.log('attendance', 'useEffect', isAttendance);
    if (!isAttendance) {
      postMutation.mutate();
    }
  }, [isAttendance]);

  return (
    <header className="flex flex-row justify-between mx-2.5 my-5">
      <Link to="/home">
        <Logo />
      </Link>
      <div className="flex flex-row justify-between items-center space-x-2">
        <div className="flex flex-row items-center">
          <CoinIcon width={25} height={25} />
          <span className="ml-1 text-sm font-bold text-gray-800">
            {/* {{Number(100)}.toLocaleString('ko-kr')} */}
            {coin}
          </span>
        </div>
        <Link to="/alarm">
          <AlarmIcon className="" />
        </Link>
      </div>
      {modalOpen && (
        <AttendanceModal date={streak} onClose={() => setModalOpen(false)} />
      )}
    </header>
  );
};

export default Header;
