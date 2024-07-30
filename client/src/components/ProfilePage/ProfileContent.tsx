import { userState } from "atoms/atoms";
import CoinIcon from "icons/CoinIcon";
import FriendIcon from "icons/FriendIcon";
import ProfilePickIcon from "icons/ProfilePickIcon";
import { useRecoilValue } from "recoil";


const ProfileContent = () => {
  // const [accessToken, setAccessToken] = useRecoilState(accessTokenState);
  // const accessToken = useRecoilValue(isLoginState);
  const profile = useRecoilValue(userState);
  const userYear: number = +profile.birth.split('-')[0];
  const year = new Date().getFullYear();
  const age = userYear - year + 1;
  console.log();
  return (
    <div
      style={{ backgroundColor: '#000855', opacity: '80%' }}
      className=" text-white mx-4 rounded-xl h-72 p-3">
      <div className="flex justify-between">
        <img className="w-28 h-28 mt-6 ml-2" src="icons/Profile.png" alt="profile" />
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
            <FriendIcon width={30} height={22} isDefault={false} />
            <div className="luckiest_guy mt-1.5">230</div>
          </div>
        </div>
      </div>

      <div className="ml-5 my-6">
        <div className="my-1">{profile.name}</div>
        <div className="my-1">{profile.campus}캠퍼스 • {profile.th}기 • {profile.classNum}반</div>
        <div className="my-1">{profile.gender} • 26세 •  {profile.mbti} • {profile.major}</div>
      </div>
    </div>
  )
}

export default ProfileContent;