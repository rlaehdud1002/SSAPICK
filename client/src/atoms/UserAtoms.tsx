import { ISendUser, IUser, IUserAdd, IUserInfo } from 'atoms/User.type';
import { atom, selector } from 'recoil';
import { persistAtom } from './RecoilPersist';


// 유저 입력 정보
export const sendUserInfoState = atom<ISendUser>({
  key: 'sendUserInfoState',
  default: undefined,
});

export const userInfostate = atom<IUserInfo>({
  key: 'userInfostate',
  default: {
  id: 0,
  username: "",
  name: "",
  profileImage: "",
  gender: "",
  cohort: 0,
  campusName: "",
  section: 0,
  pickco: 0,
  pickCount: 0,
  followingCount: 0,
  hints: [],

  },
})

// 로그인 상태
export const isLoginState = selector<boolean>({
  key: 'isLoginState',
  get: ({ get }) => {
    const accessToken = get(accessTokenState);
    return accessToken !== undefined && accessToken !== '';
  },
});

export const accessTokenState = atom<string | undefined>({
  key: 'accessTokenState',
  default: undefined,
  effects_UNSTABLE: [persistAtom],
});

export const firebaseTokenState = atom<string>({
  key: 'firebaseTokenState',
  default: "",
  effects_UNSTABLE: [persistAtom],
})

export const refreshRequestState = atom<boolean>({
  key: 'refreshRequestState',
  default: false,
  effects_UNSTABLE: [persistAtom]
})


export const userCoinState = atom<number>({
  key: 'userCoinState',
  default: 0,
});

export const userAttendanceState = atom<boolean>({
  key: 'userAttendanceState',
  default: false,
});

// 유저 프로필 이미지
export const profileImageState = atom<File | undefined>({
  key: 'profileImageState',
  default: undefined,
});