import { accessTokenState } from "atoms/UserAtoms";
import axios from "axios";
import { getRecoil } from "recoil-nexus";

const BASE_URL = process.env.REACT_APP_BACKEND_PROD_HOST;

export const KAKAO_AUTH_URL = `${BASE_URL}/oauth2/authorization/kakao`;
export const GOOGLE_AUTH_URL = `${BASE_URL}/oauth2/authorization/google`;

const instance = axios.create({
  baseURL: BASE_URL + "/api/v1",
  headers: {
    "Content-Type": "application/json",
    Accept: "application/json",
  },
});

instance.interceptors.request.use(
  (config) => {
    const accessToken = getRecoil(accessTokenState);
    console.log(accessToken);
    if (accessToken) {
      config.headers.Authorization = `Bearer ${accessToken}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

instance.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default instance;

// * 이거 사용자 생성 ( mm인증 해야됨 )이 지금 안되서 테스트 못해봄
export const SignOut = async () => {
  console.log(
    "로그아웃인데 이게 지금 사용자 생성이 안되서 테스트 못해봐요 사용자 생성이 되고 DB랑 연결되면 그떄 테스트해보세요"
  );
  try {
    await instance.post("auth/sign-out");
    // * 여기에 로그아웃 성공시 처리를 넣어주세요 ~
    window.location.href = "/";
  } catch (error) {
    console.error("로그아웃 실패:", error);
  }
};