import React from 'react';
import Header from './components/common/Header';
import Footer from './components/common/Footer';
import Login from './pages/Login';
import UserInfo from './pages/UserInfo';
import UserAddInfo from './pages/UserAddInfo';
import Home from './pages/Home';
import Ranking from './pages/Ranking';
import Pick from './pages/Pick';
import Message from './pages/Message';
import Profile from './pages/Profile';

import { Routes, Route } from 'react-router-dom';


function App() {
  return (
    <div className="flex flex-col relative">
<<<<<<< HEAD
      {/* <Login/>
      <Login/>
      <UserInfo/>
      <UserAddInfo/> */}
      <div className="flex flex-col min-h-screen">
        <Header />
=======
      <Header />
>>>>>>> c86676b3c850fc4f590eeb482a1c19b7154915ee
        <div className="flex-grow">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/home" element={<Home />} />
            <Route path="/ranking" element={<Ranking isRanked={true} />} />
            <Route path="/pick" element={<Pick />} />
            <Route path="/message" element={<Message />} />
            <Route path="/profile" element={<Profile />} />
          </Routes>
        </div>
      {/* <Login/> */}
      {/* <UserInfo/> */}
      {/* <UserAddInfo/> */}
      <div className="flex flex-col min-h-screen">
        <Footer />
      </div>
    </div>
  );
}

export default App;
