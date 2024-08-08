import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import CommonRoute from "components/Routes/CommonRoute";
import ProfileRoute from "components/Routes/ProfileRoute";
import { Route, Routes, useLocation, useNavigate } from "react-router-dom";
import { useRecoilState, useRecoilValue, useSetRecoilState } from "recoil";
import Footer from "./components/common/Footer";
import Header from "./components/common/Header";
import { validState } from "atoms/ValidAtoms";
import { initializeApp } from "firebase/app";
import NotFoundPage from "pages/NotFoundPage";
import { useEffect } from "react";
import { validCheck } from "api/validApi";
import { accessTokenState, isLoginState, refreshRequestState } from "atoms/UserAtoms";
import { refresh } from "api/authApi";

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
  const location = useLocation().pathname.split("/")[1];
  const queryClient = new QueryClient();

  const navigate = useNavigate();
  const [ValidState, setValidState] = useRecoilState(validState);
  const [refreshRequest, setRefreshRequest] = useRecoilState(refreshRequestState);
  const setAccessToken = useSetRecoilState(accessTokenState);
  const isAuthenticated = useRecoilValue(isLoginState);

  const headerFooter = () => {
    return (
      location !== "" && // 로그인 페이지
      location !== "splash" && // 스플래시 페이지
      location !== "mattermost" && // mm 인증 페이지
      location !== "404" && // 404 페이지
      location !== "infoinsert" // 추가 정보 입력 페이지
    );
  };

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

  useEffect(() => {
    if (isAuthenticated) {
      navigate("/home");
    }
  }, [isAuthenticated, navigate]);

  useEffect(() => {
    const checkValidity = async () => {
      try {
        if (location === "splash") {
          return;
        }
        const data = await validCheck();
        setValidState(data);
        if (data.lockedUser) {
          navigate("/");
          return;
        }
        if (!data.mattermostConfirmed) {
          navigate("/mattermost");
          return;
        }
        if (!data.validInfo && !location.includes("infoinsert")) {
          navigate("/infoinsert");
          return;
        }
      } catch (error) {
        console.error("유효성 검사 실패", error);
        navigate("/"); // 유효성 검사 실패 시 로그인 페이지로 리다이렉트
      }
    };
    checkValidity();
  }, [location, navigate, setValidState]);

  useEffect(() => {
    // 스크롤을 항상 최하단으로 이동
    window.scrollTo(0, document.body.scrollHeight);
  });

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
            <div className="flex flex-col max-h-screen">{headerFooter() && <Footer />}</div>
          </div>
        </div>
      </div>
    </QueryClientProvider>
  );
}

export default App;
