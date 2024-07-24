import CoinIcon from "icons/CoinIcon";
import FriendIcon from "icons/FriendIcon";
import ProfilePickIcon from "icons/ProfilePickIcon";


const ProfileContent = () => {
  // const [accessToken, setAccessToken] = useRecoilState(accessTokenState);
  // const accessToken = useRecoilValue(isLoginState);
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
            <FriendIcon width={22} height={22} />
            <div className="luckiest_guy mt-1.5">230</div>
          </div>
        </div>
      </div>

      <div className="ml-5 my-6">
        <div className="my-1">민준수</div>
        <div className="my-1">광주캠퍼스 • 11기 • 2반</div>
        <div className="my-1">남자 • 26세 •  ENTJ • 정보통신학과</div>
      </div>
    </div>
  )
}

export default ProfileContent;