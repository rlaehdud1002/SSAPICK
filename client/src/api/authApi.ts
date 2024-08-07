import { IAuth, JwtToken } from "atoms/Auth.type";
import { BaseResponse, IUserInfo } from "atoms/User.type";
import instance from "./clientApi";
import { access } from "fs";

// 유저 정보 조회
export const getUserInfo = async (): Promise<IUserInfo> => {
  const {
    data: { success, data, message },
  } = await instance.get<BaseResponse<IUserInfo>>("/user/me");

  if (!success) {
    console.log(message)
    throw new Error('유저 정보 조회 실패');
  }
  return data;
};

// 유저 정보 전송
export const UserSend = async (
  userdata: FormData,
): Promise<void> => {
  const {
    data: { success },
  } = await instance.patch<BaseResponse<null>>('/user', userdata, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  });

  if (!success) {
    throw new Error("유저 정보 전송 실패");
  }

  console.log('postMessageSend');
};

// mm 인증 요청
export const mmAuthSend = async (authData: IAuth): Promise<void> => {
  const {
    data: { success, data, message, status },
  } = await instance.post("/auth/mattermost-confirm", authData);
  console.log(status);
  if (!success) {
    console.log(message);
    throw new Error("실패");
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
  >("/auth/mattermost-confirm");
  if (!success) {
    throw new Error(message);
  }
  return data.authenticated;
};

// 로그아웃 요청
export const signOut = async (accessToken:string): Promise<void> => {
  const {
    data: { success, data, message },
  } = await instance.post("/auth/sign-out", accessToken);
  if (!success) {
    throw new Error(message);
  }
  return data;
};

// 회원 탈퇴 요청
export const withdrawal = async (): Promise<void> => {
  const {
    data: { success, data, message },
  } = await instance.delete("/auth");
  if (!success) {
    throw new Error(message);
  }
  return data;
};


export const refresh = async (): Promise<JwtToken> => {
  const {
    data: {success, data, message}
  } = await instance.post<BaseResponse<JwtToken>>("/auth/refresh")
  if (!success) {
    throw new Error(message);
  }
  return data;
}