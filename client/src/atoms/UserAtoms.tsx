import { atom, selector } from 'recoil';
import { User, UserAdd } from './User.type';

export const isLoginState = selector<boolean>({
  key: 'isLoginState',
  get: ({ get }) => {
    const accessToken = get(accessTokenState);
    return accessToken !== undefined && accessToken !== '';
  },
});

export const accessTokenState = atom<string>({
  key: 'accessTokenState',
  default: "",
});


export const userState = atom<User>({
  key: 'userState',
  default: {
    profileImage: "icons/Profile.png",
    name: "박싸피",
    gender: "남자",
    th: "11",
    campusName: "서울",

  },
});

export const userAddState = atom<UserAdd>({
  key: 'userAddState',
  default: {
    mbti: "",
    classNum: 1,
    major: "",
    birth: "",
    location: "",
    interest: ""
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