import { IFriend } from 'atoms/Friend.type';
import { IPickCreate } from 'atoms/Pick.type';
import BaseImageIcon from 'icons/BaseImageIcon';

interface ChoiceProps {
  friend: IFriend;
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
      {friend.profileImage ? (
        <img
          src={friend.profileImage}
          alt="profileImage"
          className="w-[75px] h-[75px] rounded-full"
        />
      ) : (
        <BaseImageIcon width={75} height={75} />
      )}

      <p className="pt-2 text-sm">{friend.name}</p>
    </div>
  );
};

export default Choice;
