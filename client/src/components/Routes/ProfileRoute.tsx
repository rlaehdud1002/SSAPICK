import Attendance from 'pages/AttendancePage';
import Block from 'pages/BlockPage';
import LocationAlarm from 'pages/LocationAlarmPage';
import ModiUserAddInfo from 'pages/ModiUserAddInfoPage';
import ModiUserInfo from 'pages/ModiUserInfoPage';
import QuestionList from 'pages/QuestionListPage';
import SetAccount from 'pages/SetAccountPage';
import SetAlarm from 'pages/SetAlarmPage';
import FriendList from 'pages/FriendListPage';
import FriendSearch from 'components/FriendListPage/FriendSearch';
import BlockFriend from 'components/BlockPage/BlockFriend';
import BlockQuestion from 'components/BlockPage/BlockQuestion';
import QuestionInfo from 'components/QuestionListPage/QuestionInfo';
import MakeQuestion from 'components/QuestionListPage/MakeQuestion';
import Profile from 'pages/ProfilePage';
import { Routes, Route, Navigate } from 'react-router-dom';

const ProfileRoute = () => {
  return (
    <Routes>
      <Route path="/" element={<Profile />} />
      <Route path="/modiuserinfo" element={<ModiUserInfo />} />
      <Route path="/modiuseraddinfo" element={<ModiUserAddInfo />} />
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
      <Route path="/setaccount" element={<SetAccount />} />
      <Route path="/friendsearch" element={<FriendSearch />} />
      {/* 잘못된 접근일 때 */}
      <Route path="*" element={<Navigate to="/404" />} />
    </Routes>
  );
};
export default ProfileRoute;
