import React from 'react';

import Header from './components/common/Header';
import Footer from './components/common/Footer';
import Login from './pages/Login';
import UserInfo from './pages/UserInfo';
import UserAddInfo from './pages/UserAddInfo';

import { Routes, Route } from 'react-router-dom';

import Home from './pages/Home';
import Ranking from './pages/Ranking';
import Pick from './pages/Pick';
import Message from './pages/Message';
import Profile from './pages/Profile';

function App() {
  return (
    <div className="flex flex-col relative">
      <Header />
      <Login/>
      {/* <Login/> */}
      {/* <UserInfo/> */}
      <UserAddInfo/>
      <Footer />
      <div className="flex flex-col min-h-screen">
        <Header />
        <div className="flex-grow">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/home" element={<Home />} />
            <Route path="/ranking" element={<Ranking />} />
            <Route path="/pick" element={<Pick />} />
            <Route path="/message" element={<Message />} />
            <Route path="/profile" element={<Profile />} />
          </Routes>
        </div>
        <Footer />
      </div>
    </div>
  );
}

export default App;
