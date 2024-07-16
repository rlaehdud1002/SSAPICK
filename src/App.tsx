import React from 'react';
import Header from './components/common/Header';
import Footer from './components/common/Footer';
import Login from './pages/Login';
import UserInfo from './pages/UserInfo';
import UserAddInfo from './pages/UserAddInfo';

function App() {
  return (
    <div className="flex flex-col relative">
      <Header />
      {/* <Login/> */}
      {/* <UserInfo/> */}
      <UserAddInfo/>
      <Footer />
    </div>
  );
}

export default App;
