import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import CommonRoute from "components/Routes/CommonRoute";
import ProfileRoute from "components/Routes/ProfileRoute";
import { Route, Routes, useLocation, useNavigate } from "react-router-dom";
import { useRecoilState, useRecoilValue, useSetRecoilState } from "recoil";
import Footer from "./components/common/Footer";
import Header from "./components/common/Header";
import { ReactQueryDevtools } from "@tanstack/react-query-devtools";

import { isValidateState, validState } from "atoms/ValidAtoms";

import { initializeApp } from "firebase/app";
import NotFoundPage from "pages/NotFoundPage";
import { useEffect } from "react";
import { validCheck } from "api/validApi";
import {
  accessTokenState,
  firebaseTokenState,
  isLoginState,
  refreshRequestState,
} from "atoms/UserAtoms";
import { getMessaging, getToken, onMessage } from "firebase/messaging";
import { registerToken } from "api/notificationApi";
import { setRecoil } from "recoil-nexus";

import { isBrowser, isMobile } from "react-device-detect";
import DisableDevicePage from "pages/DisableDevicePage";
import { requestPermission } from "firebase-messaging-sw";

const firebaseConfig = {
  apiKey: process.env.REACT_APP_FIREBASE_API_KEY,
  authDomain: process.env.REACT_APP_FIREBASE_AUTH_DOMAIN,
  projectId: process.env.REACT_APP_FIREBASE_PROJECT_ID,
  storageBucket: process.env.REACT_APP_FIREBASE_STORAGE_BUCKET,
  messagingSenderId: process.env.REACT_APP_FIREBASE_MESSAGING_SENDER_ID,
  appId: process.env.REACT_APP_FIREBASE_APP_ID,
  measurementId: process.env.REACT_APP_FIREBASE_MEASUREMENT_ID,
};

const firebaseApp = initializeApp(firebaseConfig);
const messaging = getMessaging(firebaseApp);



function App() {
  const location = useLocation().pathname.split("/")[1];
  const queryClient = new QueryClient();
  const [accessToken, _] = useRecoilState(accessTokenState);

  const setFirebaseToken = useSetRecoilState(firebaseTokenState);

  requestPermission(messaging);
  
  onMessage(messaging, (payload) => {
    console.log("Message received. ", payload);
  });


  const navigate = useNavigate();
  const isValid = useRecoilValue(isValidateState);
  const setValidState = useSetRecoilState(validState);
  const isAuthenticated = useRecoilValue(isLoginState);
  const headerFooter = () => {
    return (
      location !== "" && // 로그인 페이지
      location !== "splash" && // 스플래시 페이지
      location !== "mattermost" && // mm 인증 페이지
      location !== "404" && // 404 페이지
      location !== "infoinsert" && // 추가 정보 입력 페이지
      location !== "install" && // 설치 가이드 페이지
      location !== "guide" && // 가이드 페이지
      location !== "disabledevice" // 비활성화된 디바이스 페이지
    );
  };

  useEffect(() => {
    const checkValidity = async () => {
      try {
        if (isBrowser || location === "splash" || location === "guide" || location === "install") {
          return;
        }
        if (isValid) return;
        if (accessToken === "") {
          navigate("/");
          return;
        }
        const data = await validCheck();
        setValidState(data);
        if (data.lockedUser) {
          navigate("/");
          return;
        } else if (!data.mattermostConfirmed) {
          navigate("/mattermost");
          return;
        } else if (!data.validInfo) {
          navigate("/infoinsert");
          return;
        } else if (!data.lockedUser && data.mattermostConfirmed && data.validInfo) {
          if (
            location.includes("infoinsert") ||
            location.includes("mattermost") ||
            location.includes("")
          ) {
            navigate("/home");
            return;
          }
        }
      } catch (error) {
        console.error("유효성 검사 실패", error);
        navigate("/");
      }
    };
    checkValidity();
  }, [isAuthenticated, isValid, location, navigate, setValidState]);

  useEffect(() => {
    if (isBrowser && location !== "splash" && location !== "install") {
      navigate("/splash");
    }
    if (isMobile) {
      if (window.innerWidth > 430) {
        navigate("/disabledevice");
      }
      if (location === "splash" || location === "install") {
        navigate("/home");
      }
    }
  }, [location, navigate]);

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
