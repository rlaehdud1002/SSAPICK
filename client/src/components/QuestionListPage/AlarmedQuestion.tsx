import { useQueryClient } from '@tanstack/react-query';
import { IPick } from 'atoms/Pick.type';
import AlarmCheckModal from 'components/modals/AlarmCheckModal';
import UserMaskIcon from 'icons/UserMaskIcon';
import { useState } from 'react';

interface AlarmedQuestionProps {
  pick: IPick;
}

const AlarmedQuestion = ({ pick }: AlarmedQuestionProps) => {
  const [show, setShow] = useState<boolean>(false);
  const queryClient = useQueryClient();

  const handleAlarmUpdate = (pickId: number) => {
    console.log(pickId);
    setShow(true);
    queryClient.invalidateQueries({ queryKey: ['pick'] });
  };

  return (
    <div>
      <div className="flex flex-row items-center mt-5">
        <UserMaskIcon
          pickId={pick.id}
          alarm={pick.alarm}
          gen={pick.sender.gender}
          onAlarmUpdate={handleAlarmUpdate}
        />
        {/* <img src={pick.question.category.thumbnail} alt="noImage" width={50} height={50} className='bg-white/50 rounded-full p-1'/> */}
        <span className="ml-4">{pick.question.content}</span>
        {/* <div className='bg-gray-400 rounded-lg p-1 text-white'>
          삭제
        </div> */}
      </div>
      {show && (
        <AlarmCheckModal setShow={setShow} question={pick.question.content} />
      )}
    </div>
  );
};

export default AlarmedQuestion;
