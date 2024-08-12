import { IFriend } from "atoms/Friend.type";
import { IPickCreate } from "atoms/Pick.type";
import BaseImageIcon from "icons/BaseImageIcon";

interface ChoiceProps {
  isTouchDisabled: boolean;
  friend: IFriend;
  questionId: number;
  userPick: (data: IPickCreate) => void;
}

const Choice = ({ isTouchDisabled, friend, questionId, userPick }: ChoiceProps) => {
  const handlePick = () => {
    if (!isTouchDisabled) {
      userPick({
        receiverId: friend.userId,
        questionId: questionId,
        status: "PICKED",
      });
    }
  };

  return (
    <div
      className={`w-32 h-32 rounded-3xl flex flex-col items-center justify-center m-2 transition-colors duration-300 ${
        isTouchDisabled ? "bg-gray-300/50" : "bg-white/50"
      }`}
      onClick={handlePick}
    >
      {friend.profileImage ? (
        <img
          src={friend.profileImage}
          alt="profileImage"
          className={`w-[75px] h-[75px] rounded-full transition-filter duration-300 ${
            isTouchDisabled ? "filter brightness-50" : ""
          }`}
        />
      ) : (
        <BaseImageIcon
          width={75}
          height={75}
          className={isTouchDisabled ? "filter brightness-50" : ""}
        />
      )}

      <p className="pt-2 text-sm">{friend.name}</p>
    </div>
  );
};

export default Choice;
