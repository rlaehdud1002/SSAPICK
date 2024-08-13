import { useQueryClient } from '@tanstack/react-query';
import { IPick } from 'atoms/Pick.type';
import UserMaskIcon from 'icons/UserMaskIcon';

interface AlarmedQuestionProps {
  pick: IPick;
}

const AlarmedQuestion = ({ pick }: AlarmedQuestionProps) => {
  const queryClient = useQueryClient();

  const handleAlarmUpdate = (pickId: number) => {
    queryClient.invalidateQueries({ queryKey: ['pick'] });
  };

  return (
    <div
      style={{ backgroundColor: '#000855', opacity: '70%' }}
      className="flex flex-col items-center justify-center rounded-lg h-16 "
    >
      <div className="flex">
        <UserMaskIcon
          pickId={pick.id}
          alarm={pick.alarm}
          gen={pick.sender.gender}
          onAlarmUpdate={handleAlarmUpdate}
        />
        <span className="ml-2 text-white">{pick.question.content}</span>
      </div>
    </div>
  );
};

export default AlarmedQuestion;
