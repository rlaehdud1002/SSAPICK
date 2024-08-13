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
    setShow(true);
    queryClient.invalidateQueries({ queryKey: ['pick'] });
  };

  return (
    <div>
      <div 
      style={{ backgroundColor: "#000855", opacity: "70%" }}
      className="flex flex-col items-center justify-center rounded-lg h-16 ">
        <div className='flex'>
        <UserMaskIcon
          pickId={pick.id}
          alarm={pick.alarm}
          gen={pick.sender.gender}
          onAlarmUpdate={handleAlarmUpdate}
        />
        <span className="ml-2 text-white">{pick.question.content}</span>
      </div>
      </div>
      {/* <div
        className="bg-gray-400 rounded-lg px-2 py-1 text-white"
        onClick={() => handleAlarmUpdate(pick.id)}
      >
        삭제
      </div> */}
      {show && (
        <AlarmCheckModal setShow={setShow} question={pick.question.content} />
      )}
    </div>
  );
};

export default AlarmedQuestion;
