import Received from 'components/MessagePage/Received';
import Send from 'components/MessagePage/Send';
import Alarm from 'pages/AlarmPage';
import AuthCallback from 'pages/AuthCallbackPage';
import Home from 'pages/HomePage';
import Login from 'pages/LoginPage';
import Mattermost from 'pages/MattermostPage';
import Message from 'pages/MessagePage';
import Pick from 'pages/PickPage';
import Profile from 'pages/ProfilePage';
import Ranking from 'pages/RankingPage';
import Splash from 'pages/SplashPage';
import { Route, Routes } from 'react-router-dom';

const CommonRoute = () => {
  return (
    <Routes>
      <Route path="/" element={<Login />} />
      <Route path="/splash" element={<Splash />} />
      <Route path="/alarm" element={<Alarm />} />
      <Route path="/home" element={<Home />} />
      <Route path="/ranking" element={<Ranking />} />
      <Route path="/pick" element={<Pick />} />
      <Route path="/message" element={<Message />}>
        <Route index element={<Received />} />
        <Route path="received" element={<Received />} />
        <Route path="send" element={<Send />} />
      </Route>
      <Route path="/profile" element={<Profile />} />
      <Route path="/mattermost" element={<Mattermost />} />
      <Route path="/auth/callback" element={<AuthCallback />} />
    </Routes>
  );
};
export default CommonRoute;
