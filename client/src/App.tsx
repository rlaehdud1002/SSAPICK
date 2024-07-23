import Attendance from 'pages/Attendance';
import Block from 'pages/Block';
import Freind from 'pages/Friend';
import LocationAlarm from 'pages/LocationAlarm';
import ModiUserInfo from 'pages/ModiUserInfo';
import QuestionList from 'pages/QuestionList';
import SetAccont from 'pages/SetAccount';
import SetAlarm from 'pages/SetAlarm';
import Footer from './components/common/Footer';
import Header from './components/common/Header';
import ModiUserAddInfo from 'pages/ModiUserAddInfo';


import Alarm from 'pages/Alarm';
import { Route, Routes } from 'react-router-dom';
import { RecoilRoot } from 'recoil';

function App() {
  return (

    <div className="flex flex-col relative">
      <RecoilRoot>
        <div className="flex flex-col max-h-screen">
          <Header />
          <div className="flex-grow">
            <Routes>
              
              
            </Routes>

            
            <div className="flex flex-col max-h-screen">
              <Footer />
            </div>
          </div>
        </div>
      </RecoilRoot>
    </div>

  );
}

export default App;
