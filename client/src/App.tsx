import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import CommonRoute from 'components/Routes/CommonRoute';
import ProfileRoute from 'components/Routes/ProfileRoute';
import { Route, Routes, useLocation, useNavigate } from 'react-router-dom';
import { useRecoilState } from 'recoil';
import Footer from './components/common/Footer';
import Header from './components/common/Header';

import { validCheck } from 'api/validApi';
import { validState } from 'atoms/ValidAtoms';

import { initializeApp } from 'firebase/app';
import { useEffect } from 'react';
import NotFoundPage from 'pages/NotFoundPage';

const firebaseConfig = {
  apiKey: process.env.REACT_APP_FIREBASE_API_KEY,
  authDomain: process.env.REACT_APP_FIREBASE_AUTH_DOMAIN,
  projectId: process.env.REACT_APP_FIREBASE_PROJECT_ID,
  storageBucket: process.env.REACT_APP_FIREBASE_STORAGE_BUCKET,
  messagingSenderId: process.env.REACT_APP_FIREBASE_MESSAGING_SENDER_ID,
  appId: process.env.REACT_APP_FIREBASE_APP_ID,
  measurementId: process.env.REACT_APP_FIREBASE_MEASUREMENT_ID,
};

function App() {
  initializeApp(firebaseConfig);
  const location = useLocation().pathname.split('/')[1];
  console.log(location);
  const queryClient = new QueryClient();

  const navigate = useNavigate();
  const [ValidState, setValidState] = useRecoilState(validState);

  const headerFooter = () => {
    if (
      location !== '' && // 로그인 페이지
      location !== 'splash' && // 스플래시 페이지
      location !== 'mattermost' && // mm 인증 페이지
      location !== '404' // 404 페이지
    ) {
      return true;
    } else {
      return false;
    }
  };

  useEffect(() => {
    const checkValidity = async () => {
      try {
        console.log('location', location);
        console.log('ValidState', ValidState);
        if (location === 'splash') {
          return;
        }
        const data = await validCheck();
        setValidState(data);
        console.log('data', data);
        if (data.lockedUser) {
          navigate('/');
          return;
        }
        if (!data.mattermostConfirmed) {
          navigate('/mattermost');
          return;
        }
        if (!data.validInfo) {
          navigate('/userinfo');
          return;
        }
        if (data.validInfo) {
          navigate('/home');
          return;
        }
      } catch (error) {
        console.error('유효성 검사 실패', error);
        navigate('/'); // 유효성 검사 실패 시 로그인 페이지로 리다이렉트
      }
    };

    checkValidity();
  }, [navigate, setValidState]);

  return (
    <QueryClientProvider client={queryClient}>
      <div className="flex flex-col relative">
        <div className="flex flex-col max-h-screen">
          {headerFooter() && <Header />}
          <div className="flex-grow">
            <Routes>
              <Route path="/*" element={<CommonRoute />} />
              <Route path="/profile/*" element={<ProfileRoute />} />
              <Route path="/404" element={<NotFoundPage />} />
            </Routes>
            <div className="flex flex-col max-h-screen">
              {headerFooter() && <Footer />}
            </div>
          </div>
        </div>
      </div>
    </QueryClientProvider>
  );
}

export default App;
