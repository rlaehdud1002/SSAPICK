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

export const SignOut = async () => {
  try {
    await instance.post("auth/sign-out");
    // 여기에 로그아웃 성공시 처리를 넣어주세요.
    window.location.href = "/";
  } catch (error) {
    console.error("로그아웃 실패:", error);
  }
};
