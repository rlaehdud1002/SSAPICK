import Footer from './components/common/Footer';
import Header from './components/common/Header';
import Home from './pages/Home';
import Message from './pages/Message';
import Pick from './pages/Pick';
import Profile from './pages/Profile';
import Ranking from './pages/Ranking';
import MattermostDone from 'pages/MattermostDone';

import { Route, Routes } from 'react-router-dom';

function App() {
  return (
    <div className="flex flex-col relative">
      <div className="flex flex-col max-h-screen">
        <Header />
        {/* <div className="flex-grow">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/home" element={<Home />} />
            <Route path="/ranking" element={<Ranking isRanked={true} />} />
            <Route path="/pick" element={<Pick />} />
            <Route path="/message" element={<Message />} />
            <Route path="/profile" element={<Profile />} />
            </Routes>
        </div> */}
        <MattermostDone/>
        <div className="flex flex-col max-h-screen">
          <Footer />
        </div>
      </div>
    </div>
  );
}

export default App;
