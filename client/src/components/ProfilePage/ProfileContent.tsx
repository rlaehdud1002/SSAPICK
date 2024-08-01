
import { userAddState, userCoinState, userFriendState, userPickState, userState } from "atoms/UserAtoms";
import CoinIcon from "icons/CoinIcon";
import FriendIcon from "icons/FriendIcon";
import ProfilePickIcon from "icons/ProfilePickIcon";
import { useRecoilValue } from "recoil";


const ProfileContent = () => {
  // const [accessToken, setAccessToken] = useRecoilState(accessTokenState);
  // const accessToken = useRecoilValue(isLoginState);
  const profile = useRecoilValue(userState);
  const friend = useRecoilValue(userFriendState);
  const coin = useRecoilValue(userCoinState);
  const pick = useRecoilValue(userPickState);
  const profileAdd = useRecoilValue(userAddState);

  const userYear: number = +profileAdd.birth.split('-')[0];
  const year = new Date().getFullYear();
  const age = year - userYear + 1;
  console.log(age);
  return (
    <div
      style={{ backgroundColor: '#000855', opacity: '80%' }}
      className=" text-white mx-4 rounded-xl h-72 p-3">
      <div className="flex justify-between">
        <img className="w-28 h-28 mt-6 ml-2" src={profile.profileImage} alt="profile" />
        <div className="flex items-center mr-2">
          <div className="flex flex-col items-center mx-1">
            <CoinIcon width={30} height={30} />
            <span className="luckiest_guy my-1">{coin}</span>
          </div>
          <div className="flex flex-col items-center mx-1">
            <ProfilePickIcon width={30} height={30} />
            <span className="luckiest_guy my-1">{pick}</span>
          </div>
          <div className="flex flex-col items-center ml-1">
            <FriendIcon width={30} height={22} isDefault={false} />
            <div className="luckiest_guy mt-2">{friend}</div>
          </div>
        </div>
      </div>

      <div className="ml-5 my-6">
        <div className="my-1">{profile.name}</div>
        <div className="my-1">{profile.campusName}캠퍼스 • {profile.th}기 • {profileAdd.classNum}반</div>
        <div className="my-1">{profile.gender} • {age}세 •  {profileAdd.mbti} • {profileAdd.major}</div>
      </div>
    </div>
  )
}

export default ProfileContent;