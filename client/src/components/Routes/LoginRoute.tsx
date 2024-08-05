import UserAddInfo from 'pages/UserAddInfoPage';
import NotFoundPage from 'pages/NotFoundPage';
import UserInfo from 'pages/UserInfoPage';
import { Route, Routes } from 'react-router-dom';

const LoginRoute = () => {
  return (
    <Routes>
      <Route path="/userinfo" element={<UserInfo />} />
      <Route path="/useraddinfo" element={<UserAddInfo />} />
      {/* 잘못된 접근일 때 */}
      <Route path="*" element={<NotFoundPage />} />
    </Routes>
  );
};

export default LoginRoute;
