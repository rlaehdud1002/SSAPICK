import { IPickCreate } from 'atoms/Pick.type';

interface ChoiceProps {
  friend: any;
  questionId: number;
  userPick: (data: IPickCreate) => void;
}

const Choice = ({ friend, questionId, userPick }: ChoiceProps) => {
  const handlePick = () => {
    userPick({
      receiverId: friend.userId,
      questionId: questionId,
      status: 'PICKED',
    });
  };

  return (
    <div
      className="bg-white/50 w-32 h-32 rounded-3xl flex flex-col items-center justify-center first-line m-2"
      onClick={handlePick}
    >
      <img
        src={friend?.profileImage || '/images/default_profile.png'}
        alt="profileImage"
        className="w-[75px] h-[75px] rounded-full"
      />
      <p className="pt-2 text-sm">{friend?.nickname || ''}</p>
    </div>
  );
};

export default Choice;
