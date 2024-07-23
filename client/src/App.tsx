import UserInfo from 'pages/UserInfo';
import Footer from './components/common/Footer';
import Header from './components/common/Header';
import Home from './pages/Home';
import Message from './pages/Message';
import Pick from './pages/Pick';
import Profile from './pages/Profile';
import Ranking from './pages/Ranking';
import UserAddInfo from 'pages/UserAddInfo';
import ModiUserInfo from 'pages/ModiUserInfo';
import SetAlarm from 'pages/SetAlarm';
import Freind from 'pages/Friend';
import Block from 'pages/Block';
import Attendance from 'pages/Attendance';
import LocationAlarm from 'pages/LocationAlarm';
import QuestionList from 'pages/QuestionList';
import SetAccont from 'pages/SetAccount';



import { Route, Routes } from 'react-router-dom';
import Alarm from 'pages/Alarm';

function App() {
  return (
    
    <div className="flex flex-col relative">
      <div className="flex flex-col max-h-screen">
        <Header />
        <div className="flex-grow">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/alarm" element={<Alarm />} />
            <Route path="/home" element={<Home />} />
            <Route path="/ranking" element={<Ranking isRanked={true} />} />
            <Route path="/pick" element={<Pick />} />
            <Route path="/message" element={<Message />} />
            <Route path="/profile" element={<Profile />} />
            <Route path="/UserInfo" element={<UserInfo />} />
            <Route path="/UserAddInfo" element={<UserAddInfo />} />
            <Route path="/ModiUserInfo" element={<ModiUserInfo />} />
            <Route path="/SetAlarm" element={<SetAlarm />} />
            <Route path="/Friend" element={<Freind/>} />
            <Route path="/Block" element={<Block/>} />
            <Route path="/Attendance" element={<Attendance/>} />
            <Route path="/LocationAlarm" element={<LocationAlarm/>} />
            <Route path="/QuestionList" element={<QuestionList/>} />
            <Route path="/SetAccount" element={<SetAccont/>} />
            </Routes>
        <div className="flex flex-col max-h-screen">
          <Footer />
        </div>
      </div>
    </div>
  </div>
   
  );
}

export default App;
