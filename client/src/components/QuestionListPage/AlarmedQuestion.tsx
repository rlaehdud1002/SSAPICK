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
    <div className="flex flex-row justify-between items-center">
      <div className="flex flex-row">
        <UserMaskIcon
          pickId={pick.id}
          alarm={pick.alarm}
          gen={pick.sender.gender}
          onAlarmUpdate={handleAlarmUpdate}
        />
        <span className="ml-4">{pick.question.content}</span>
      </div>
      {show && (
        <AlarmCheckModal setShow={setShow} question={pick.question.content} />
      )}
    </div>
  );
};

export default AlarmedQuestion;
