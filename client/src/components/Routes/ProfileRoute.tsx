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
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import BlockFriend from 'components/BlockPage/BlockFriend';
import BlockQuestion from 'components/BlockPage/BlockQuestion';
import QuestionInfo from 'components/QuestionListPage/QuestionInfo';
import MakeQuestion from 'components/QuestionListPage/MakeQuestion';


const ProfileRoute = () => {
  return(
    <Routes>
      <Route path="/profile/modiuserinfo" element={<ModiUserInfo />} />
              <Route path="/profile/modiuseraddinfo" element={<ModiUserAddInfo />} />
              <Route path="/profile/setalarm" element={<SetAlarm />} />
              <Route path="/profile/friendlist" element={<FriendList />} />
              <Route path="/profile/block" element={<Block/>}>
                <Route path="" element={<BlockFriend/>}/>
                <Route path="blockfriend" element={<BlockFriend/>}/>
                <Route path="blockquestion" element={<BlockQuestion/>}/>
              </Route>
              <Route path="/profile/attendance" element={<Attendance />} />
              <Route path="/profile/locationalarm" element={<LocationAlarm />} />
              <Route path="/profile/questionlist" element={<QuestionList />}>
                <Route path="" element={<QuestionInfo/>}/>
                <Route path="questioninfo" element={<QuestionInfo/>}/>
                <Route path="makequestion" element={<MakeQuestion/>}/>
              </Route>
              <Route path="/profile/setaccount" element={<SetAccount />} />
              <Route path="/profile/friendsearch" element={<FriendSearch/>} />
    </Routes>
    
  )
}
export default ProfileRoute;