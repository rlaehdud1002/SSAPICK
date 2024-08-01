import BackIcon from 'icons/BackIcon';
import CoinIcon from 'icons/CoinIcon';
import AttendanceAward from 'components/AttendancePage/AttendanceAward';
import AttendanceCheck from 'components/AttendancePage/AttendanceCheck';

import { useNavigate } from 'react-router-dom';
import AttendanceModal from 'components/modals/AttendanceModal';

const Attendance = () => {
  const nav = useNavigate();
  return (
    <div className="m-2">
      <div
        className="flex flex-row items-center cursor-pointer"
        onClick={() => nav(-1)}
      >
        <BackIcon />
      </div>
      <div className="text-center mt-3 text-3xl">출석체크</div>
      <div className="text-center">
        <AttendanceModal date={7} />
      </div>
      <div className="bg-[#5F86E9]/50 mt-7 rounded-lg text-white text-center w-3/5 mx-auto p-2">
        <b className="luckiest_guy">7</b>일 연속 출석중!
      </div>
      <AttendanceCheck date={7} />
      <div className="flex flex-row justify-center items-center bg-[#E2E3F4] rounded-lg w-3/5 mx-auto mb-4 py-2">
        <CoinIcon width={30} height={30} />
        <span className="mx-2">
          <b className="luckiest_guy">14</b>일 출석 완료!
        </span>
        <CoinIcon width={30} height={30} />
      </div>
      <AttendanceAward />
    </div>
  );
};

export default Attendance;
