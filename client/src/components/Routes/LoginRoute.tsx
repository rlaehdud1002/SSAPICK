import Login from 'pages/Login';
import UserAddInfo from 'pages/UserAddInfo';
import UserInfo from 'pages/UserInfo';
import { Route, Routes } from 'react-router-dom';


const LoginRoute = () => {
  return (
<Routes>
<Route path="/userinfo" element={<UserInfo />} />
<Route path="/useraddinfo" element={<UserAddInfo />} />
<Route path="/login" element={<Login />} />
</Routes>
  )
}

export default LoginRoute;