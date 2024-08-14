import { useQueryClient } from '@tanstack/react-query';
import CoolTimeIcon from 'icons/CoolTimeIcon';
import { useEffect, useState } from 'react';

interface CoolTimeProps {
  endTime: string;
}

const CoolTime = ({ endTime }: CoolTimeProps) => {
  const coolTime = new Date(endTime).getTime();
  const [timeLeft, setTimeLeft] = useState<number>(coolTime);

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

  const queryClient = useQueryClient();

  if (timeLeft <= 0) {
    queryClient.invalidateQueries({
      queryKey: ['pickInfo'],
    });
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
        {Number(minutes) > 0 && (
          <p className="luckiest_guy">{minutes}분 </p>
        )}
        <p className="luckiest_guy ms-1">{seconds}</p>초 후에{' '}
        <p className="luckiest_guy ms-1">ssapick</p> 할 수 있어요!
      </div>
    </div>
  );
};

export default CoolTime;
