import { IUser, IUserAdd } from 'atoms/User.type';
import { atom, selector } from 'recoil';
import { persistAtom } from './RecoilPersist';


export const isLoginState = selector<boolean>({
  key: 'isLoginState',
  get: ({ get }) => {
    const accessToken = get(accessTokenState);
    return accessToken !== undefined && accessToken !== '';
  },
});

export const accessTokenState = atom<string>({
  key: 'accessTokenState',
  default: '',
  effects_UNSTABLE: [persistAtom],
});

export const firebaseTokenState = atom<string>({
  key: 'firebaseTokenState',
  default: "",
  effects_UNSTABLE: [persistAtom],
})


export const userState = atom<IUser>({
  key: 'userState',
  default: {
    profileImage: '',
    name: '',
    th: '',
    campusName: '',
    gender: '',
  }
});

export const userAddState = atom<IUserAdd>({
  key: 'userAddState',
  default: {
    mbti: '',
    classNum: 1,
    major: '',
    birth: '',
    location: '',
    interest: '',
  },
});

export const userCoinState = atom<number>({
  key: 'userCoinState',
  default: 0,
});

export const userFriendState = atom<number>({
  key: 'userFriendState',
  default: 0,
});

export const userPickState = atom<number>({
  key: 'userPickState',
  default: 0,
});

export const userAttendanceState = atom<boolean>({
  key: 'userAttendanceState',
  default: false,
});
