import { IAuth } from 'atoms/Auth.type';
import instance from './clientApi';
import { useRecoilValue } from 'recoil';
import { accessTokenState } from 'atoms/UserAtoms';

// mm 인증 요청
export const mmAuthSend = async (authData: IAuth): Promise<void> => {
  const {
    data: { success, data, message },
  } = await instance.post('/auth/mattermost-confirm', authData);
  if (!success) {
    console.log(Error(message));
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

// // * 이거 사용자 생성 ( mm인증 해야됨 )이 지금 안되서 테스트 못해봄
// export const signOut = async (authorizationToken:string) => {
//   console.log(

//     "로그아웃인데 이게 지금 사용자 생성이 안되서 테스트 못해봐요 사용자 생성이 되고 DB랑 연결되면 그떄 테스트해보세요"
//   );
//   try {
//     await instance.post("auth/sign-out", authorizationToken);
//     // * 여기에 로그아웃 성공시 처리를 넣어주세요 ~
//     window.location.href = "/";
//   } catch (error) {
//     console.error("로그아웃 실패:", error);
//   }
// };
