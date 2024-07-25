import Attendance from 'pages/Attendance';
import Block from 'pages/Block';
import LocationAlarm from 'pages/LocationAlarm';
import ModiUserAddInfo from 'pages/ModiUserAddInfo';
import ModiUserInfo from 'pages/ModiUserInfo';
import QuestionList from 'pages/QuestionList';
import SetAccount from 'pages/SetAccount';
import SetAlarm from 'pages/SetAlarm';
import FriendList from 'pages/FriendList';
import FriendSearch from 'components/FriendListPage/FriendSearch';
import { Route, Routes } from 'react-router-dom';

const ProfileRoute = () => {
  return(
    <Routes>
      <Route path="/profile/modiuserinfo" element={<ModiUserInfo />} />
              <Route path="/profile/modiuseraddinfo" element={<ModiUserAddInfo />} />
              <Route path="/profile/setalarm" element={<SetAlarm />} />
              <Route path="/profile/friendlist" element={<FriendList />} />
              <Route path="/profile/block" element={<Block />} />
              <Route path="/profile/attendance" element={<Attendance />} />
              <Route path="/profile/locationalarm" element={<LocationAlarm />} />
              <Route path="/profile/questionlist" element={<QuestionList />} />
              <Route path="/profile/setaccount" element={<SetAccount />} />
              <Route path="/profile/friendsearch" element={<FriendSearch/>} />
    </Routes>

  )
}
export default ProfileRoute;