import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import CommonRoute from 'components/Routes/CommonRoute';
import ProfileRoute from 'components/Routes/ProfileRoute';
import { Route, Routes, useLocation, useNavigate } from 'react-router-dom';
import { useRecoilState, useRecoilValue, useSetRecoilState } from 'recoil';
import Footer from './components/common/Footer';
import Header from './components/common/Header';
import { ReactQueryDevtools } from '@tanstack/react-query-devtools';

import { isValidateState, validState } from 'atoms/ValidAtoms';

import { initializeApp } from "firebase/app";
import NotFoundPage from "pages/NotFoundPage";
import { useEffect } from "react";
import { validCheck } from "api/validApi";
import { accessTokenState, firebaseTokenState, isLoginState, refreshRequestState } from "atoms/UserAtoms";
import { refresh } from "api/authApi";
import { getMessaging, getToken, onMessage } from "firebase/messaging";
import { messaging } from "firebase-messaging-sw";
import { registerToken } from "api/notificationApi";
import { setRecoil } from "recoil-nexus";



function App() {
  const location = useLocation().pathname.split('/')[1];
  const queryClient = new QueryClient();

  const setFirebaseToken = useSetRecoilState(firebaseTokenState);

  useEffect(() => {
    Notification.requestPermission()
      .then((permission) => {
        if (permission === 'granted') {
          console.log("run this method")
          getToken(messaging, { vapidKey: process.env.REACT_APP_FIREBASE_VAPID_KEY })
            .then((token: string) => {
              registerToken(token).then(() => {
                setFirebaseToken(token);
              }).catch((error) => {
    
              })
            })
            .catch((err) => {
          })
          onMessage(messaging , (payload) => {
            console.log(payload)
          })
        } else if (permission === 'denied') {
        }
      })
    
  }, [])

  onMessage(messaging, (payload) => {
    console.log('Message received. ', payload);
  });

  const navigate = useNavigate();
  const isValid = useRecoilValue(isValidateState);
  const setValidState = useSetRecoilState(validState);
  const isAuthenticated = useRecoilValue(isLoginState);
  const headerFooter = () => {
    return (
      location !== '' && // 로그인 페이지
      location !== 'splash' && // 스플래시 페이지
      location !== 'mattermost' && // mm 인증 페이지
      location !== '404' && // 404 페이지
      location !== 'infoinsert' && // 추가 정보 입력 페이지
      location !== 'install' // 설치 가이드 페이지
    );
  };

  useEffect(() => {
    const checkValidity = async () => {
      try {
        if (location === 'splash' || location === 'install') {
          return;
        }
        if (isValid) return;
        const data = await validCheck();
        setValidState(data);
        if (data.lockedUser) {
          navigate('/');
          return;
        } else if (!data.mattermostConfirmed) {
          navigate('/mattermost');
          return;
        } else if (!data.validInfo) {
          navigate('/infoinsert');
          return;
        } else if (
          !data.lockedUser &&
          data.mattermostConfirmed &&
          data.validInfo
        ) {
          if (
            location.includes('infoinsert') ||
            location.includes('mattermost') ||
            location.includes('')
          ) {
            navigate('/home');
            return;
          }
        }
      } catch (error) {
        console.error('유효성 검사 실패', error);
        navigate('/');
      }
    };
    checkValidity();
  }, [isAuthenticated, isValid, location, navigate, setValidState]);

  return (
    <QueryClientProvider client={queryClient}>
      <ReactQueryDevtools buttonPosition="top-left" initialIsOpen={false} />
      <div className="flex flex-col relative min-h-screen">
        {headerFooter() && <Header />}
        <main className="flex-grow mb-[70px] relative">
          <Routes>
            <Route path="/*" element={<CommonRoute />} />
            <Route path="/profile/*" element={<ProfileRoute />} />
            <Route path="/404" element={<NotFoundPage />} />
          </Routes>
        </main>
        {headerFooter() && <Footer />}
      </div>
    </QueryClientProvider>
  );
}

export default App;
  