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
import { accessTokenState, isLoginState, refreshRequestState } from "atoms/UserAtoms";
import { refresh } from "api/authApi";
import { getMessaging, onMessage } from "firebase/messaging";
import { requestPermission } from "firebase-messaging-sw";

// Firebase 설정
const firebaseConfig = {
  apiKey: process.env.REACT_APP_FIREBASE_API_KEY,
  authDomain: process.env.REACT_APP_FIREBASE_AUTH_DOMAIN,
  projectId: process.env.REACT_APP_FIREBASE_PROJECT_ID,
  storageBucket: process.env.REACT_APP_FIREBASE_STORAGE_BUCKET,
  messagingSenderId: process.env.REACT_APP_FIREBASE_MESSAGING_SENDER_ID,
  appId: process.env.REACT_APP_FIREBASE_APP_ID,
  measurementId: process.env.REACT_APP_FIREBASE_MEASUREMENT_ID,
};

initializeApp(firebaseConfig);

const messaging = getMessaging();

function App() {
  const queryClient = new QueryClient();
  const location = useLocation().pathname.split("/")[1];
  const navigate = useNavigate();

  // Recoil 상태 및 setter
  const isValid = useRecoilValue(isValidateState);
  const setValidState = useSetRecoilState(validState);
  const [refreshRequest, setRefreshRequest] = useRecoilState(refreshRequestState);
  const setAccessToken = useSetRecoilState(accessTokenState);

  // Firebase 메시징 설정
  useEffect(() => {
    onMessage(messaging, (payload) => {
      console.log("Message received. ", payload);
    });
    requestPermission(messaging);
  }, []);

  // 토큰 리프레시 처리
  useEffect(() => {
    if (!refreshRequest) {
      refresh()
        .then((response) => {
          setAccessToken(response.accessToken);
          setRefreshRequest(true);
        })
        .catch((error) => {
          console.error(error);
          setRefreshRequest(true);
        });
    }
  }, [refreshRequest, setAccessToken, setRefreshRequest]);

  // 유효성 검사 및 리다이렉션 처리
  useEffect(() => {
    const checkValidity = async () => {
      try {
        if (location === "splash" || location === "") return;

        if (isValid) return;

        const data = await validCheck();
        console.log("유효성 검사", data);

        setValidState(data);

        if (data.lockedUser) {
          navigate("/");
        } else if (!data.mattermostConfirmed) {
          navigate("/mattermost");
        } else if (!data.validInfo) {
          navigate("/infoinsert");
        } else if (data.mattermostConfirmed && data.validInfo) {
          if (location === "splash" || location === "mattermost" || location === "infoinsert") {
            navigate("/home");
          }
        }
      } catch (error) {
        console.error("유효성 검사 실패", error);
        navigate("/");
      }
    };

    checkValidity();
  }, [isValid, location, navigate, setValidState]);

  // 헤더 및 푸터 렌더링 조건
  const shouldShowHeaderFooter = () => {
    return !["", "splash", "mattermost", "404", "infoinsert"].includes(location);
  };

  return (
    <QueryClientProvider client={queryClient}>
      <ReactQueryDevtools initialIsOpen={false} />
      <div className="flex flex-col relative min-h-screen">
        {shouldShowHeaderFooter() && <Header />}
        <main className="flex-grow mb-[70px]">
          <Routes>
            <Route path="/*" element={<CommonRoute />} />
            <Route path="/profile/*" element={<ProfileRoute />} />
            <Route path="/404" element={<NotFoundPage />} />
          </Routes>
        </main>
        {shouldShowHeaderFooter() && <Footer />}
      </div>
    </QueryClientProvider>
  );
}

export default App;
