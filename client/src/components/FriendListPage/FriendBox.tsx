import PopOver from "components/common/Popover";


interface FriendProps {
  campus: string;
  campusSection: number;
  name: string;
  campusDescription: string;
}

const Friend = ({ campus, campusDescription, name, campusSection }: FriendProps) => {
  return (
    <div className="flex flex-col relative">
      <div className="flex items-center ml-5 mr-5 justify-between">
        <img
          className="w-16 h-16 ml-6"
          src="../icons/Profile.png"
          alt="profile"
        />
        <div className="flex flex-col">
          <span>
            {campus}캠퍼스 {campusSection}반 {name}
          </span>
          <div className='flex flex-row'>
            <span className="text-xs bg-white/50 rounded-xl w-[56px] text-center mr-2">
              반 친구
            </span>
            <span className="text-xs bg-white/50 rounded-xl w-[56px] text-center">
              찐친
            </span>
          </div>
        </div>
        <PopOver />
      </div>
      <div className="bg-white h-px w-90 mx-8 mt-5"></div>
    </div>
  );
};

export default Friend;
