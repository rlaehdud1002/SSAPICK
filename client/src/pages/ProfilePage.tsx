import { useQuery } from "@tanstack/react-query";
import { getAlarm } from "api/alarmApi";
import { getUserInfo } from "api/authApi";
import { alarmSettingsState } from "atoms/AlarmAtoms";
import { IUser } from "atoms/User.type";
import { accessTokenState } from "atoms/UserAtoms";
import ProfileAlarm from "components/ProfilePage/ProfileAlarm";
import ProfileContent from "components/ProfilePage/ProfileContent";
import AccountIcon from "icons/AccountIcon";
import AttendanceIcon from "icons/AttendanceIcon";
import BlockIcon from "icons/BlockIcon";
import FriendAlarmIcon from "icons/FriendAlarmIcon";
import LocationAlarmIcon from "icons/LocationAlarmIcon";
import QuestionAlarmIcon from "icons/QuestionAlarmIcon";
import SetAlarmIcon from "icons/SetAlarmIcon";
import UserInfoIcon from "icons/UserInfoIcon";
import { Link, useNavigate } from "react-router-dom";
import { useRecoilState } from "recoil";
import { alarmSettingsState } from "atoms/AlarmAtoms";
import { useRecoilValue } from "recoil";
import { getAlarm } from "api/alarmApi";
import { IAlarm } from "atoms/Alarm.type";
import { useQuery } from "@tanstack/react-query";
import { IUser, IUserInfo } from "atoms/User.type";
import { getUserInfo } from "api/authApi";

const Profile = () => {
  const { data: information, isLoading } = useQuery<IUserInfo>({
    queryKey: ["information"],
    queryFn: async () => await getUserInfo(),
  });

  const accessToken = useRecoilValue(accessTokenState);
  console.log(accessToken);

  const [alarmSettings, setAlarmSettings] = useRecoilState(alarmSettingsState);
  const navigate = useNavigate();

  const getAlarmData = async () => {
    try {
      const alarmData = await getAlarm();
      setAlarmSettings(alarmData);
      navigate("/profile/setalarm", { state: { alarmSettings: alarmData } });
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div>
      {information && <ProfileContent information={information} />}
      <div className="mb-20">
        <Link to="/profile/modiuserinfo">
          <ProfileAlarm title="개인정보 수정" content="힌트로 제공할 나의 정보 수정">
            <UserInfoIcon width={50} height={50} />
          </ProfileAlarm>
        </Link>
        <div onClick={getAlarmData}>
          <ProfileAlarm title="알림 설정" content="원하는 알림 설정">
            <SetAlarmIcon width={50} height={50} />
          </ProfileAlarm>
        </div>
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
        <Link to="/profile/setaccount">
          <ProfileAlarm title="계정 설정" content="나의 계정 설정">
            <AccountIcon width={50} height={50} />
          </ProfileAlarm>
        </Link>
      </div>
    </div>
  );
};

export default Profile;
