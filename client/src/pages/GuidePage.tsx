import AlarmGuide from 'components/GuidePage/AlarmGuide';
import AttendenceGuide from 'components/GuidePage/AttendenceGuide';
import FriendGuide from 'components/GuidePage/FriendGuide';
import HomeGuide from 'components/GuidePage/HomeGuide';
import LocationGuide from 'components/GuidePage/LocationGuide';
import MessageGuide from 'components/GuidePage/MessageGuide';
import PickGuide from 'components/GuidePage/PickGuide';
import ProfileGuide from 'components/GuidePage/ProfileGuide';
import RankingGuide from 'components/GuidePage/RankingGuide';

import MoveLoginPage from 'components/GuidePage/MoveLoginPage';
//  순서는 footer에 있는 메뉴 순서대로
const Guide = () => {
  return (
    <div className="flex flex-col items-center">
      <span className="text-3xl mt-10 luckiest_guy text-blue-400">
        ssapick guide
      </span>
      {/* 홈 */}
      <HomeGuide />
      {/* 랭킹 */}
      <RankingGuide />
      {/* 픽 */}
      <PickGuide />
      {/* 메세지 */}
      <MessageGuide />
      {/* 위치 */}
      <LocationGuide />
      {/* 친구 찾기 */}
      <FriendGuide />
      {/* 출석 */}
      <AlarmGuide />
      <AttendenceGuide />
      <ProfileGuide />
      {/* 로그인 페이지 이동 버튼 */}
      <MoveLoginPage />
    </div>
  );
};

export default Guide;
