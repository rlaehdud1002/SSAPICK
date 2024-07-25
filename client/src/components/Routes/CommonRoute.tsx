import Received from 'components/MessagePage/Received';
import Send from 'components/MessagePage/Send';
import Alarm from 'pages/Alarm';
import Home from 'pages/Home';
import Message from 'pages/Message';
import Pick from 'pages/Pick';
import Profile from 'pages/Profile';
import Ranking from 'pages/Ranking';
import { Route, Routes } from 'react-router-dom';

const CommonRoute = () => {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/alarm" element={<Alarm />} />
      <Route path="/home" element={<Home />} />
      <Route path="/ranking" element={<Ranking />} />
      <Route path="/pick" element={<Pick />} />
      <Route path="/message" element={<Message />}>
        <Route path="" element={<Received />} />
        <Route path="received" element={<Received />} />
        <Route path="send" element={<Send />} />
      </Route>
      <Route path="/profile/" element={<Profile />} />
    </Routes>
  );
};
export default CommonRoute;
