import { JwtToken } from 'atoms/Auth.type';
import { BaseResponse } from 'atoms/User.type';
import { accessTokenState, isLoginState } from 'atoms/UserAtoms';
import axios from 'axios';
import { getRecoil, setRecoil } from 'recoil-nexus';

const BASE_URL = process.env.REACT_APP_BACKEND_PROD_HOST;

export const host =
  process.env.NODE_ENV === 'development'
    ? 'http://localhost:3000'
    : 'https://www.ssapick.kro.kr';
export const KAKAO_AUTH_URL = `${BASE_URL}/oauth2/authorization/kakao?redirect_uri=${host}`;
export const GOOGLE_AUTH_URL = `${BASE_URL}/oauth2/authorization/google?redirect_uri=${host}`;
const REFRESH_URI = `${BASE_URL}/api/v1/auth/refresh`;

const instance = axios.create({
  baseURL: BASE_URL + '/api/v1',
  headers: {
    'Content-Type': 'application/json',
    Accept: 'application/json',
  },
  withCredentials: true,
});

instance.interceptors.request.use(
  (config) => {
    const accessToken = getRecoil(accessTokenState);
    if (accessToken) {
      config.headers.Authorization = `Bearer ${accessToken}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  },
);

instance.interceptors.response.use(
  (response) => {
    return response;
  },
  async (error) => {
    const {
      config,
      response: { status },
    } = error;
    const originalRequest = config;
    const isLogin = getRecoil(isLoginState);

    // 인증 에러가 아닌 경우 바로 에러 처리
    if (status !== 401) {
      if (status.toString().startsWith('5')) {
        console.log('server error');
      }
      return Promise.reject(error);
    }

    // Refresh Token 재발급 중 에러가 발생한 경우
    if (config.url === REFRESH_URI) {
      setRecoil(accessTokenState, undefined);
      throw new Error('인증 기간이 만료되었습니다. 다시 로그인 해주세요.');
    }

    try {
      const {
        data: {
          success,
          message,
          data: { accessToken: newAccessToken },
        },
      } = await instance.post<BaseResponse<JwtToken>>(REFRESH_URI);
      if (!success) throw new Error(message);

      originalRequest.headers.Authorization = `Bearer ${newAccessToken}`;
      setRecoil(accessTokenState, newAccessToken);
      return axios(originalRequest);
    } catch (error) {
      setRecoil(accessTokenState, undefined);
      throw new Error('인증 과정에서 에러가 발생하였습니다.');
    }
  },
);

export default instance;
