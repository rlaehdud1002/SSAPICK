import Received from 'components/MessagePage/Received';
import Send from 'components/MessagePage/Send';
import Alarm from 'pages/AlarmPage';
import AuthCallback from 'pages/AuthCallbackPage';
import CoolTime from 'pages/CoolTimePage';
import Home from 'pages/HomePage';
import InfoInsert from 'pages/InfoInsert';
import InstallGuidePage from 'pages/InstallGuidePage';
import Login from 'pages/LoginPage';
import Mattermost from 'pages/MattermostPage';
import Message from 'pages/MessagePage';
import ModiInfoInsert from 'pages/ModiInfoInsert';
import Pick from 'pages/PickPage';
import Ranking from 'pages/RankingPage';
import Splash from 'pages/SplashPage';
import { Navigate, Route, Routes } from 'react-router-dom';

const CommonRoute = () => {
  return (
    <Routes>
      <Route path="/" element={<Login />} />
      <Route path="/splash" element={<Splash />} />
      <Route path="/install" element={<InstallGuidePage />} />
      <Route path="/alarm" element={<Alarm />} />
      <Route path="/home" element={<Home />} />
      <Route path="/ranking" element={<Ranking />} />
      <Route path="/pick" element={<Pick />} />
      <Route path="/cooltime" element={<CoolTime />} />
      <Route path="/message" element={<Message />}>
        <Route index element={<Received />} />
        <Route path="received" element={<Received />} />
        <Route path="send" element={<Send />} />
      </Route>
      <Route path="/mattermost" element={<Mattermost />} />
      <Route path="/auth/callback" element={<AuthCallback />} />
      {/* 추가 정보 입력 */}
      <Route path="/infoinsert" element={<InfoInsert />} />
      <Route path="/modiinfoinsert" element={<ModiInfoInsert />} />
      {/* 잘못된 접근일 때 */}
      <Route path="*" element={<Navigate to="/404" />} />
    </Routes>
  );
};
export default CommonRoute;
