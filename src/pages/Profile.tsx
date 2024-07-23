import CoinIcon from "icons/CoinIcon";
import FriendIcon from "icons/FriendIcon";
import ProfilePickIcon from "icons/ProfilePickIcon";

const Profile = () => {
  return (
    <div
      style={{ backgroundColor: '#000855', opacity: '80%' }}
      className="text-white mx-4 rounded-xl p-3 pb-1">
      <div className="flex justify-between">
        <img className="w-28 h-28" src="icons/Profile.png" alt="profile" />
        <div className="flex items-center">
          <div className="mx-1">
          <CoinIcon width={30} height={30} />
          <span className="luckiest_guy mx-2">42</span>
          </div>
          <div className="mx-1">
          <ProfilePickIcon width={30} height={30} />
          <span className="luckiest_guy mx-2">96</span>
          </div>
          <div className="mx-2">
          <FriendIcon width={22} height={22}/>
          <div className="luckiest_guy mt-1.5">230</div>
          </div>
        </div>
        </div>

        <div>
        <h2 className="mt-5">박사피</h2>
        <div>광주캠퍼스 • 11기 • 2반</div>
        <div>남자 • 26세 •  ENTJ • 정보통신학과</div>
        </div>
          
    </div>
  );
};

export default Profile;
