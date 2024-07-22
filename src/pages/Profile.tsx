import CoinIcon from "icons/CoinIcon";
import ProfilePickIcon from "icons/ProfilePickIcon";

const Profile = () => {
  return (
    <div
      style={{ backgroundColor: '#000855', opacity: '80%' }}
      className="text-white mx-4 rounded-md p-3 pb-1">
      <div className="flex justify-between">
        <img className="w-28 h-28" src="icons/Profile.png" alt="profile" />
        <div className="flex">
          <CoinIcon width={30} height={30} />
          <ProfilePickIcon width={30} height={30} />
        </div>
      </div>
    </div>
  );
};

export default Profile;
