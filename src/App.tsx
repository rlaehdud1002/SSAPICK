import React from 'react';
import Header from './components/common/Header';
import Footer from './components/common/Footer';
import Login from './pages/Login';

function App() {
  return (
    <div className="flex flex-col relative">
      <Header />
      <Login/>
      <Footer />
    </div>
  );
}

export default App;
