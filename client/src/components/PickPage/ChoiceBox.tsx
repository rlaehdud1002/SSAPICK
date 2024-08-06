import { IPickCreate } from 'atoms/Pick.type';

interface ChoiceProps {
  friend: any;
  questionId: number;
  userPick: (data: IPickCreate) => void;
}

const Choice = ({ friend, questionId, userPick }: ChoiceProps) => {
  const handlePick = () => {
    const pickData = {
      receiverId: friend.id,
      questionId: questionId,
      index: 0,
      status: 'PICKED',
    };

    userPick(pickData);
  };

  return (
    <div
      className="bg-white/50 w-32 h-32 rounded-3xl flex flex-col items-center justify-center first-line m-2"
      onClick={handlePick}
    >
      <img
        src={friend.profileImage}
        alt="profileImage"
        className="w-[75px] h-[75px] rounded-full"
      />
      <p className="pt-2 text-sm">{friend.nickname}</p>
    </div>
  );
};

export default Choice;
