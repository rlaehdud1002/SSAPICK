import { IAuth } from 'atoms/Auth.type';
import instance from './clientApi';
import { useRecoilValue } from 'recoil';
import { accessTokenState } from 'atoms/UserAtoms';

// mm 인증 요청
export const mmAuthSend = async (authData: IAuth): Promise<void> => {
  console.log('mm')
  const {
    data: { success, data, message, status },
  } = await instance.post('/auth/mattermost-confirm', authData);
  console.log(status)
  if (!success) {
    console.log(message);
    throw new Error("실패");
  }
  return data;
};

// mm 인증 확인 요청
export const mmAuthConfirm = async (): Promise<void> => {
  const {
    data: { success, data, message },
  } = await instance.get('/auth/mattermost-confirm');
  if (!success) {
    throw new Error(message);
  }
  return data;
};

// 로그아웃 요청
export const signOut = async (authToken: string): Promise<void> => {
  const {
    data: { success, data, message },
  } = await instance.post('/auth/sign-out', authToken);
  if (!success) {
    throw new Error(message);
  }
  return data;
};
 // 회원 탈퇴 요청
export const withdrawal = async (): Promise<void> => {
  const {
    data: { success, data, message },
  } = await instance.delete('/auth',);
  if (!success) {
    throw new Error(message);
  }
  return data;
}