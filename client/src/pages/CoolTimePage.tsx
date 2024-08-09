import { endCoolTimeState } from 'atoms/PickAtoms';
import CoolTimeIcon from 'icons/CoolTimeIcon';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

const CoolTime = () => {
  const coolTime = useRecoilValue<number>(endCoolTimeState);
  const [timeLeft, setTimeLeft] = useState<number>(coolTime);
  const navigate = useNavigate();

  useEffect(() => {
    const updateTimeLeft = () => {
      const now = new Date().getTime();
      const remainingTime = coolTime - now;
      setTimeLeft(remainingTime);
    };

    updateTimeLeft(); // 컴포넌트 마운트 시 초기값 설정
    const timer = setInterval(updateTimeLeft, 1000); // 1초마다 시간 업데이트

    return () => clearInterval(timer); // 컴포넌트 언마운트 시 타이머 정리
  }, [coolTime]);

  const minutes = String(Math.floor((timeLeft / (1000 * 60)) % 60));
  const seconds = String(Math.floor((timeLeft / 1000) % 60));

  if (timeLeft <= 0) {
    navigate('/pick');
  }

  return (
    <div
      className="w-full flex flex-col items-center justify-center"
      style={{ height: 'calc(100vh - 70px)' }}
    >
      <CoolTimeIcon
        width={100}
        height={100}
        className="flex items-center justify-center"
      />
      <p className="text-[20px] my-2">새로운 질문 준비중</p>
      <div className="flex flex-row items-center text-[12px]">
        <p className="luckiest_guy pt-1">{minutes}</p>분{' '}
        <p className="luckiest_guy ms-1 pt-1">{seconds}</p>초 후에{' '}
        <p className="luckiest_guy ms-1 pt-1">ssapick</p> 할 수 있어요!
      </div>
    </div>
  );
};

export default CoolTime;
