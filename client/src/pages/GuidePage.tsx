import RankingGuide from "components/GuidePage/RankingGuide";
import PickGuide from "components/GuidePage/PickGuide";
import HomeGuide from "components/GuidePage/HomeGuide";
import MessageGuide from "components/GuidePage/MessageGuide";
import AlarmGuide from "components/GuidePage/AlarmGuide";
import ProfileGuide from "components/GuidePage/ProfileGuide";

//  순서는 footer에 있는 메뉴 순서대로
const Guide = () => {
  return (
    <div className="flex flex-col items-center">
      {/* 홈 */}
      <HomeGuide />
      {/* 랭킹 */}
      <RankingGuide />
      {/* 픽 */}
      <PickGuide />
      {/* 메세지 */}
      <MessageGuide/>
      {/* 위치 */}
      <AlarmGuide/>
      <ProfileGuide/>
      {/* 로그인 페이지 이동 버튼 */}
    </div>
  );
};

export default Guide;
