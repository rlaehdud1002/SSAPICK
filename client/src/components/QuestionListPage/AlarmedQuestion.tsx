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
    </div>
  );
};

export default AlarmedQuestion;
