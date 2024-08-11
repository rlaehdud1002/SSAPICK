import { IUserInfo } from 'atoms/User.type';
import BaseImageIcon from 'icons/BaseImageIcon';
import CoinIcon from 'icons/CoinIcon';
import FriendIcon from 'icons/FriendIcon';
import ProfilePickIcon from 'icons/ProfilePickIcon';

interface ProfileContentProps {
  information: IUserInfo;
}

const ProfileContent: React.FC<ProfileContentProps> = ({ information }) => {
  const userYear = information.hints[7].content.split('-')[0];
  const year = new Date().getFullYear();
  const age = year - (userYear as unknown as number) + 1;
  

  return (
    <div
      style={{ backgroundColor: '#000855', opacity: '80%' }}
      className=" text-white mx-4 rounded-lg h-72 p-3"
    >
      <div className="flex justify-between">
        {information.profileImage ? (
          <img
            className="w-28 h-28 mt-6 ml-2 rounded-full"
            src={information?.profileImage}
            alt="profile"
          />
        ) : (
          <BaseImageIcon width={112} height={112} className="mt-6 ml-2" />
        )}

        <div className="flex items-center mr-2">
          <div className="flex flex-col items-center mx-1">
            <CoinIcon width={30} height={30} />
            <span className="luckiest_guy my-1">{information.pickco}</span>
          </div>
          <div className="flex flex-col items-center mx-1">
            <ProfilePickIcon width={30} height={30} />
            <span className="luckiest_guy my-1">{information.pickCount}</span>
          </div>
          <div className="flex flex-col items-center ml-1">
            <FriendIcon width={30} height={22} isDefault={false} />
            <div className="luckiest_guy mt-2">
              {information.followingCount}
            </div>
          </div>
        </div>
      </div>

      <div className="ml-5 my-6">
        <div className="my-1">{information?.name}</div>
        <div className="my-1">
          {information?.campusName}캠퍼스 • {information?.cohort}기 •{' '}
          {information?.section}반
        </div>
        <div className="my-1">
          {information?.gender === 'F' ? <span>여자</span> : <span>남자</span>}•{' '}
          {age}세 • {information?.hints[5].content} •{' '}
          {information?.hints[9].content}
        </div>
      </div>
    </div>
  );
};

export default ProfileContent;
