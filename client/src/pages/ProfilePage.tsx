import { useMutation, useQuery } from "@tanstack/react-query";
import { getUserInfo, signOut } from "api/authApi";
import { IUserInfo } from "atoms/User.type";
import { accessTokenState } from "atoms/UserAtoms";
import ProfileAlarm from "components/ProfilePage/ProfileAlarm";
import ProfileContent from "components/ProfilePage/ProfileContent";
import Loading from "components/common/Loading";
import SignoutModal from "components/modals/SignoutModal";
import AccountIcon from "icons/AccountIcon";
import AttendanceIcon from "icons/AttendanceIcon";
import BlockIcon from "icons/BlockIcon";
import CoinIcon from "icons/CoinIcon";
import FriendAlarmIcon from "icons/FriendAlarmIcon";
import LocationAlarmIcon from "icons/LocationAlarmIcon";
import QuestionAlarmIcon from "icons/QuestionAlarmIcon";
import SetAlarmIcon from "icons/SetAlarmIcon";
import UserInfoIcon from "icons/UserInfoIcon";
import { Link, useNavigate } from "react-router-dom";
import { useRecoilState } from "recoil";

const Profile = () => {
  // 유저 정보 조회
  const { data: information, isLoading } = useQuery<IUserInfo>({
    queryKey: ["information"],
    queryFn: async () => await getUserInfo(),
  });

  const navigate = useNavigate();
  const [accessToken, setAccessToken] = useRecoilState(accessTokenState);

  const mutation = useMutation({
    mutationFn: signOut,
    onSuccess: () => {
      console.log("로그아웃 성공");
      setAccessToken("");
      navigate("/");
    },
  });

  const onLogout = () => {
    mutation.mutate();
  };

  console.log(information);

  if (isLoading) {
    return <Loading />;
  }

  return (
    <div>
      {information && <ProfileContent information={information} />}
      <div>
        <Link to="/modiinfoinsert">
          <ProfileAlarm title="개인정보 수정" content="힌트로 제공할 나의 정보 수정">
            <UserInfoIcon width={50} height={50} />
          </ProfileAlarm>
        </Link>
        <Link to="/profile/setalarm">
          <ProfileAlarm title="알림 설정" content="원하는 알림 설정">
            <SetAlarmIcon width={50} height={50} />
          </ProfileAlarm>
        </Link>
        <Link to="/profile/friendlist">
          <ProfileAlarm title="친구 관리" content="내가 PICK하고 싶은 친구 찾기">
            <FriendAlarmIcon width={50} height={50} />
          </ProfileAlarm>
        </Link>
        <Link to="/profile/block">
          <ProfileAlarm title="차단" content="내 차단 리스트 확인">
            <BlockIcon width={50} height={50} />
          </ProfileAlarm>
        </Link>
        <Link to="/profile/attendance">
          <ProfileAlarm title="출석 체크" content="연속 출석 도전하고 PICKCO 얻자 !">
            <AttendanceIcon width={50} height={50} />
          </ProfileAlarm>
        </Link>
        <Link to="/profile/locationalarm">
          <ProfileAlarm title="내 주위 사람" content="주위 사람 찾고 PICKCO 얻자 !">
            <LocationAlarmIcon width={50} height={50} />
          </ProfileAlarm>
        </Link>
        <Link to="/profile/questionlist">
          <ProfileAlarm title="질문 리스트" content="나와 관련된 질문을 확인해보세요 !">
            <QuestionAlarmIcon width={50} height={50} />
          </ProfileAlarm>
        </Link>
        <Link to="/profile/pickcolog">
          <ProfileAlarm title="PICKCO 로그" content="나의 PICKCO 정보">
            <CoinIcon width={50} height={50} />
          </ProfileAlarm>
        </Link>

        {/* 로그아웃 버튼 */}
        <SignoutModal />
        

      </div>
    </div>
  );
};

export default Profile;
