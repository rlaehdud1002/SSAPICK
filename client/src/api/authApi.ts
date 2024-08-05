import { IAuth } from 'atoms/Auth.type';
import { BaseResponse, IUser } from 'atoms/User.type';
import instance from './clientApi';


// 유저 정보 조회
export const getUserInfo = async (): Promise<IUser[]> => {
  const {
    data: { success, data, message },
  } = await instance.get<BaseResponse<IUser[]>>('/user/me');

  if (!success) {
    throw new Error('유저 정보 조회 실패');
  }
  return data;
};

// mm 인증 요청
export const mmAuthSend = async (authData: IAuth): Promise<void> => {
  const {
    data: { success, data, message, status },
  } = await instance.post('/auth/mattermost-confirm', authData);
  console.log(status);
  if (!success) {
    console.log(message);
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
  const {
    data: { success, data, message },
  } = await instance.post('/auth/sign-out');
  if (!success) {
    throw new Error(message);
  }
  return data;
};
// 회원 탈퇴 요청
export const withdrawal = async (): Promise<void> => {
  const {
    data: { success, data, message },
  } = await instance.delete('/auth');
  if (!success) {
    throw new Error(message);
  }
  return data;
};
