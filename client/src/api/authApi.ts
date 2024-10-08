import { IAuth, JwtToken } from 'atoms/Auth.type';
import { BaseResponse, IPickco, IUserInfo } from 'atoms/User.type';
import instance from './clientApi';

// 유저 정보 조회
export const getUserInfo = async (): Promise<IUserInfo> => {
  const {
    data: { success, data },
  } = await instance.get<BaseResponse<IUserInfo>>('/user/me');

  if (!success) {
    throw new Error('유저 정보 조회 실패');
  }

  return data;
};

// 유저 정보 전송
export const UserSend = async (userdata: FormData): Promise<void> => {
  const {
    data: { success },
  } = await instance.patch<BaseResponse<null>>('/user', userdata, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  });
  if (!success) {
    throw new Error('유저 정보 전송 실패');
  }
};

// mm 인증 요청
export const mmAuthSend = async (authData: IAuth): Promise<void> => {
  const {
    data: { success, data },
  } = await instance.post('/auth/mattermost-confirm', authData);
  if (!success) {
    throw new Error('실패');
  }
  return data;
};

// mm 인증 확인 요청
export const mmAuthConfirm = async (): Promise<boolean> => {
  const {
    data: { success, data, message },
  } = await instance.get<
    BaseResponse<{
      authenticated: boolean;
    }>
  >('/auth/mattermost-confirm');
  if (!success) {
    throw new Error(message);
  }
  return data.authenticated;
};

// 로그아웃 요청
export const signOut = async (): Promise<void> => {
  const response = await instance.post('/auth/sign-out');
  console.log(response)
  if (!response.statusText.startsWith("2")) {
    throw new Error("에러 발생")
  }
};

// refresh token 요청
export const refresh = async (): Promise<JwtToken> => {
  try {
    const {
      data: { success, data, message },
    } = await instance.post<BaseResponse<JwtToken>>('/auth/refresh');
    if (!success) {
      throw new Error(message);
    }
    return data;
  } catch (error) {
    throw new Error('refresh token');
  }
};

// pickco 조회
export const getPickco = async (): Promise<IPickco> => {
  const {
    data: { success, data },
  } = await instance.get<BaseResponse<IPickco>>('/user/pickco');

  if (!success) {
    throw new Error('픽코 조회 실패');
  }

  return data;
};
