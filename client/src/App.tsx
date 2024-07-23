import UserInfo from 'pages/UserInfo';
import Footer from './components/common/Footer';
import Header from './components/common/Header';
import Home from './pages/Home';
import Message from './pages/Message';
import Pick from './pages/Pick';
import Profile from './pages/Profile';
import Ranking from './pages/Ranking';
import UserAddInfo from 'pages/UserAddInfo';

import { Route, Routes } from 'react-router-dom';
import Alarm from 'pages/Alarm';

function App() {
  return (
    <div className="flex flex-col relative">
      <div className="flex flex-col">
        <Header />
        <div className="flex-grow">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/alarm" element={<Alarm />} />
            <Route path="/home" element={<Home />} />
            <Route path="/ranking" element={<Ranking />} />
            <Route path="/pick" element={<Pick />} />
            <Route path="/message" element={<Message />} />
            <Route path="/profile" element={<Profile />} />
            <Route path="/UserInfo" element={<UserInfo />} />
            <Route path="/UserAddInfo" element={<UserAddInfo />} />
          </Routes>
          <div className="flex flex-col">
            <Footer />
          </div>
        </div>
      </div>
    </div>
  );
}

export default App;
