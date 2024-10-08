import { TOTAL_PICK_COIN } from 'coins/coins';
import { IQuestion } from 'atoms/Pick.type';
import CoinIcon from 'icons/CoinIcon';
import { useEffect } from 'react';
import { Link } from 'react-router-dom';
import { useRecoilState } from 'recoil';
import { isQuestionUpdatedState } from 'atoms/PickAtoms';

interface PickCompleteProps {
  setQuestion: React.Dispatch<React.SetStateAction<IQuestion[]>>;
}

const PickComplete = ({ setQuestion }: PickCompleteProps) => {
  const [isUpdated, setIsUpdated] = useRecoilState<boolean>(
    isQuestionUpdatedState,
  );

  useEffect(() => {
    setIsUpdated(false);
  }, []);

  return (
    <div
      className="w-full flex flex-col items-center justify-center"
      style={{ height: 'calc(100vh - 70px)' }}
    >
      <CoinIcon
        width={72}
        height={72}
        className="flex items-center justify-center"
      />
      <p className="text-[20px] my-2">축하드려요!</p>
      <div className="flex flex-row items-center text-[12px]">
        <p className="luckiest_guy pt-1.5">PICKCO {TOTAL_PICK_COIN}</p>개를
        획득하셨어요!
      </div>
      <Link to="/home">
        <div className="bg-ssapick rounded-lg py-2 px-10 text-center mt-20">
          메인 페이지로 가기!
        </div>
      </Link>
    </div>
  );
};

export default PickComplete;
