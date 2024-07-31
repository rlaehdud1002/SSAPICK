import Received from 'components/MessagePage/Received';
import Send from 'components/MessagePage/Send';
import Alarm from 'pages/Alarm';
import Home from 'pages/Home';
import Login from 'pages/Login';
import Message from 'pages/Message';
import Pick from 'pages/Pick';
import Profile from 'pages/Profile';
import Ranking from 'pages/Ranking';
import Splash from 'pages/Splash';
import { Route, Routes } from 'react-router-dom';

const CommonRoute = () => {
  return (
    <Routes>
      <Route path="/splash" element={<Splash />} />
      <Route path="/alarm" element={<Alarm />} />
      <Route path="/home" element={<Home />} />
      <Route path="/" element={<Login />} />
      <Route path="/ranking" element={<Ranking />} />
      <Route path="/pick" element={<Pick />} />
      <Route path="/message" element={<Message />}>
        <Route index element={<Received />} />
        <Route path="received" element={<Received />} />
        <Route path="send" element={<Send />} />
      </Route>
      <Route path="/profile" element={<Profile />} />
    </Routes>
  );
};
export default CommonRoute;
