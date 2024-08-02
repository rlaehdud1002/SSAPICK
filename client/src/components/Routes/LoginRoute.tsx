import UserAddInfo from 'pages/UserAddInfoPage';
import UserInfo from 'pages/UserInfoPage';
import { Route, Routes } from 'react-router-dom';

const LoginRoute = () => {
  return (
    <Routes>
      <Route path="/userinfo" element={<UserInfo />} />
      <Route path="/useraddinfo" element={<UserAddInfo />} />
    </Routes>
  );
};

export default LoginRoute;
