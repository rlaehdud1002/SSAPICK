import UserPickIcon from 'icons/UserPickIcon';

interface ChoiceProps {
  username: string;
  // profileImage: string;
}

const Choice = ({ username }: ChoiceProps) => {
  return (
    <div className="bg-white/50 w-32 h-32 rounded-3xl flex flex-col items-center justify-center first-line m-2">
      <UserPickIcon gen="female"/>
      <p className="pt-2 text-sm">{username}</p>
    </div>
  );
};

export default Choice;
