import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import CommonRoute from 'components/Routes/CommonRoute';
import LoginRoute from 'components/Routes/LoginRoute';
import ProfileRoute from 'components/Routes/ProfileRoute';
import { Route, Routes, useLocation, useNavigate } from 'react-router-dom';
import {
  RecoilRoot,
  useRecoilState,
  useRecoilValue,
  useSetRecoilState,
} from 'recoil';
import RecoilNexus from 'recoil-nexus';
import Footer from './components/common/Footer';
import Header from './components/common/Header';

import { validCheck } from 'api/validApi';
import { validState } from 'atoms/ValidAtoms';

import { initializeApp } from 'firebase/app';
import { useEffect } from 'react';

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
        }
        if (!data.mattermostConfirmed) {
          navigate('/mattermost');
        }
        if (!data.validInfo) {
          navigate('/userinfo');
        }
        if (data.validInfo) {
          navigate('/home');
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
          {location !== '' &&
            location !== 'splash' &&
            location !== 'mattermost' &&
            location !== 'login' && <Header />}
          <div className="flex-grow">
            <Routes>
              <Route path="/*" element={<CommonRoute />} />
              {/* <Route path="/login/*" element={<LoginRoute />} /> */}
              <Route path="/profile/*" element={<ProfileRoute />} />
            </Routes>
            <div className="flex flex-col max-h-screen">
              {location !== '' &&
                location !== 'splash' &&
                location !== 'mattermost' &&
                location !== 'login' && <Footer />}
            </div>
          </div>
        </div>
      </div>
    </QueryClientProvider>
  );
}

export default App;
