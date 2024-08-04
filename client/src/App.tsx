import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import CommonRoute from 'components/Routes/CommonRoute';
import LoginRoute from 'components/Routes/LoginRoute';
import ProfileRoute from 'components/Routes/ProfileRoute';
import { Route, Routes, useLocation } from 'react-router-dom';
import { RecoilRoot } from 'recoil';
import RecoilNexus from 'recoil-nexus';
import Footer from './components/common/Footer';
import Header from './components/common/Header';
import NotFoundPage from 'pages/NotFoundPage';

// ! FIXED NotFoundPage 렌더링 이상하게 됨

import { initializeApp } from "firebase/app";

const firebaseConfig = {
  apiKey: process.env.REACT_APP_FIREBASE_API_KEY,
  authDomain: process.env.REACT_APP_FIREBASE_AUTH_DOMAIN,
  projectId: process.env.REACT_APP_FIREBASE_PROJECT_ID,
  storageBucket: process.env.REACT_APP_FIREBASE_STORAGE_BUCKET,
  messagingSenderId: process.env.REACT_APP_FIREBASE_MESSAGING_SENDER_ID,
  appId: process.env.REACT_APP_FIREBASE_APP_ID,
  measurementId: process.env.REACT_APP_FIREBASE_MEASUREMENT_ID
};




function App() {
  initializeApp(firebaseConfig);
  const location = useLocation().pathname.split('/')[1];
  const queryClient = new QueryClient();

  return (
    <RecoilRoot>
      <RecoilNexus />
      <QueryClientProvider client={queryClient}>
        <div className="flex flex-col relative">
          <div className="flex flex-col max-h-screen">
            {location !== '' && location !== 'splash' && <Header />}
            <div className="flex-grow">
              <Routes>
                <Route path="/*" element={<CommonRoute />} />
                <Route path="/login/*" element={<LoginRoute />} />
                <Route path="/profile/*" element={<ProfileRoute />} />
                {/* 잘못된 접근일 때
                <Route path="*" element={<NotFoundPage />} /> */}
              </Routes>
              <div className="flex flex-col max-h-screen">
                {location !== '' && location !== 'splash' && <Footer />}
              </div>
            </div>
          </div>
        </div>
      </QueryClientProvider>
    </RecoilRoot>
  );
}

export default App;
