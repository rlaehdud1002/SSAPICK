import { ISendUser, IUser, IUserAdd, IUserAttendance, IUserInfo } from "atoms/User.type";
import { atom, selector } from "recoil";
import { persistAtom } from "./RecoilPersist";

// 유저 입력 정보
export const sendUserInfoState = atom<ISendUser>({
  key: "sendUserInfoState",
  default: undefined,
});

export const userInfostate = atom<IUserInfo>({
  key: "userInfostate",
  default: undefined,
});

// 로그인 상태
export const isLoginState = selector<boolean>({
  key: "isLoginState",
  get: ({ get }) => {
    const accessToken = get(accessTokenState);
    return accessToken !== undefined && accessToken !== "";
  },
});

export const accessTokenState = atom<string | undefined>({
  key: "accessTokenState",
  default: undefined,
  effects_UNSTABLE: [persistAtom],
});

export const firebaseTokenState = atom<string>({
  key: "firebaseTokenState",
  default: "",
  effects_UNSTABLE: [persistAtom],
});

export const refreshRequestState = atom<boolean>({
  key: "refreshRequestState",
  default: false,
  effects_UNSTABLE: [persistAtom],
});

// export const userState = atom<IUser>({
//   key: 'userState',
//   default: {
//     profileImage: '',
//     name: '',
//     th: '',
//     campusName: '',
//     gender: '',
//   }
// });

// export const userAddState = atom<IUserAdd>({
//   key: 'userAddState',
//   default: {
//     mbti: '',
//     classNum: 1,
//     major: '',
//     birth: '',
//     location: '',
//     interest: '',
//   },
// });

export const userCoinState = atom<number>({
  key: "userCoinState",
  default: 0,
});

export const userAttendanceState = atom<IUserAttendance>({
  key: "userAttendanceState",
  default: {
    todayChecked: false,
    streak: 0,
  },
});

// 유저 프로필 이미지
export const profileImageState = atom<File | undefined>({
  key: "profileImageState",
  default: undefined,
});
