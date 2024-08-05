import {
  userAddState,
  userCoinState,
  userFriendState,
  userPickState,
  userState,
} from "atoms/UserAtoms";
import { IUserInfo } from "atoms/User.type";
import CoinIcon from "icons/CoinIcon";
import FriendIcon from "icons/FriendIcon";
import ProfilePickIcon from "icons/ProfilePickIcon";
import { useRecoilValue } from "recoil";

interface ProfileContentProps {
  information: IUserInfo;
}

const ProfileContent: React.FC<ProfileContentProps> = ({ information }) => {
  // const [accessToken, setAccessToken] = useRecoilState(accessTokenState);
  // const accessToken = useRecoilValue(isLoginState);

  // const userYear: number = +profileAdd?.birth.split('-')[0] || 0;
  const year = new Date().getFullYear();
  // const age = year - userYear + 1;
  // console.log(age);
  console.log("information", information.campusName);
  return (
    <div
      style={{ backgroundColor: "#000855", opacity: "80%" }}
      className=" text-white mx-4 rounded-lg h-72 p-3"
    >
      <div className="flex justify-between">
        <img
          className="w-28 h-28 mt-6 ml-2 rounded-full"
          src={information?.profileImage}
          alt="profile"
        />
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
            <div className="luckiest_guy mt-2">{information.followingCount}</div>
          </div>
        </div>
      </div>

      <div className="ml-5 my-6">
        <div className="my-1">{information?.name}</div>
        <div className="my-1">
          {information?.campusName}캠퍼스 • {information?.cohort}기 • {information?.section}반
        </div>
        <div className="my-1">
          {information?.gender} • {0}세 • {information?.hints[5].content} •{" "}
          {information?.hints[9].content}
        </div>
      </div>
    </div>
  );
};

export default ProfileContent;
