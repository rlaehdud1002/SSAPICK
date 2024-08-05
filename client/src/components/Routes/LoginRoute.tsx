import InfoInsert from 'pages/InfoInsert';
import NotFoundPage from 'pages/NotFoundPage';
import { Route, Routes } from 'react-router-dom';

const LoginRoute = () => {
  return (
    <Routes>
      <Route path="/infoinsert" element={<InfoInsert />} />
      {/* 잘못된 접근일 때 */}
      <Route path="*" element={<NotFoundPage />} />
    </Routes>
  );
};

export default LoginRoute;
