import { DAILY_ATTENDANCE_COIN, TWO_WEEK_ATTENDANCE_COIN, WEEK_ATTENDANCE_COIN } from 'coins/coins';
import AttendanceIcon from 'icons/AttendanceIcon';
import TrophyIcon from 'icons/TrophyIcon';

const AttendanceAward = () => {
  return (
    <div className="bg-white/50 rounded-lg w-[90%] mx-auto p-4">
      <div className="flex flex-row justify-center pb-3">
        <AttendanceIcon width={30} height={30} setpage />
        <span className="mx-2">출석 보상</span>
        <AttendanceIcon width={30} height={30} setpage />
      </div>
      <div className="flex flex-row">
        <TrophyIcon width={30} height={30} />
        <p className="mx-2">
          매일 <b className="luckiest_guy">PICKCO {DAILY_ATTENDANCE_COIN}</b>개 지급
        </p>
      </div>
      <div className="flex flex-row">
        <TrophyIcon width={30} height={30} />
        <p className="mx-2">
          <b className="luckiest_guy">7</b>일 연속 출석 시{' '}
          <b className="luckiest_guy">PICKCO {WEEK_ATTENDANCE_COIN}</b>개 지급
        </p>
      </div>
      <div className="flex flex-row">
        <TrophyIcon width={30} height={30} />
        <p className="mx-2">
          <b className="luckiest_guy">14</b>일 연속 출석 시{' '}
          <b className="luckiest_guy">PICKCO {TWO_WEEK_ATTENDANCE_COIN}</b>개 지급
        </p>
      </div>
    </div>
  );
};

export default AttendanceAward;
