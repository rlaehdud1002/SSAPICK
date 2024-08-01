import AuthCallback from "pages/AuthCallbackPage";
import Login from "pages/LoginPage";
import UserAddInfo from "pages/UserAddInfoPage";
import UserInfo from "pages/UserInfoPage";
import { Route, Routes } from "react-router-dom";

const LoginRoute = () => {
  return (
    <Routes>
      <Route path="/userinfo" element={<UserInfo />} />
      <Route path="/useraddinfo" element={<UserAddInfo />} />
      {/* <Route path="/login" element={<Login />} /> */}
      <Route path="/auth/callback" element={<AuthCallback />} />
    </Routes>
  );
};

export default LoginRoute;
