import BlockFriend from "components/BlockPage/BlockFriend";
import BlockQuestion from "components/BlockPage/BlockQuestion";
import FriendSearch from "components/FriendListPage/FriendSearch";
import ModiUserInfo from "components/LoginPage/ModiUserInfo";
import MakeQuestion from "components/QuestionListPage/MakeQuestion";
import QuestionInfo from "components/QuestionListPage/QuestionInfo";
import Attendance from "pages/AttendancePage";
import Block from "pages/BlockPage";
import FriendList from "pages/FriendListPage";
import LocationAlarm from "pages/LocationAlarmPage";
import ModiInfoInsert from "pages/ModiInfoInsert";
import Pickcolog from "pages/PickcologPage";
import Profile from "pages/ProfilePage";
import QuestionList from "pages/QuestionListPage";
import SetAlarm from "pages/SetAlarmPage";
import { Navigate, Route, Routes } from "react-router-dom";

const ProfileRoute = () => {
  return (
    <Routes>
      <Route path="/" element={<Profile />} />
      {/* <Route path="/modiuserinfo" element={<ModiUser />} />
      <Route path="/modiuseraddinfo" element={<ModiUserAddInfo />} /> */}
      <Route path="/setalarm" element={<SetAlarm />} />
      <Route path="/friendlist" element={<FriendList />} />
      <Route path="/block" element={<Block />}>
        <Route index element={<BlockFriend />} />
        <Route path="blockfriend" element={<BlockFriend />} />
        <Route path="blockquestion" element={<BlockQuestion />} />
      </Route>
      <Route path="/attendance" element={<Attendance />} />
      <Route path="/locationalarm" element={<LocationAlarm />} />
      <Route path="/questionlist" element={<QuestionList />}>
        <Route index element={<QuestionInfo />} />
        <Route path="questioninfo" element={<QuestionInfo />} />
        <Route path="makequestion" element={<MakeQuestion />} />
      </Route>
      <Route path="/friendsearch" element={<FriendSearch />} />
      <Route path="/pickcolog" element={<Pickcolog />} />
      {/* <Route path="/modiinfoinsert" element={<ModiInfoInsert/>}/> */}
      {/* 잘못된 접근일 때 */}
      <Route path="*" element={<Navigate to="/404" />} />
    </Routes>
  );
};
export default ProfileRoute;
