import { atom, selector } from 'recoil';
import { User } from './User.type';

// export const isLoginState = atom<boolean>({
//   key: 'isLoginState',
//   default: false,
// });

export const accessTokenState = atom<string | undefined>({
  key: 'accessTokenState',
  default: undefined,
});

export const isLoginState = selector<boolean>({
  key: 'isLoginState',
  get: ({ get }) => {
    const accessToken = get(accessTokenState);
    return accessToken !== undefined && accessToken !== '';
  },
});

export const userState = atom<User>({
  key: 'userState',
  default: {
    profileImage:"icons/Profile.png",
    name:"민준수", 
    gender:"여자",
    th:12,
    campusName:"광주",
    mbti:"ENFP",
    classNum:2,
    major:"컴퓨터공학과",
    birth:"1996-01-07",
    town:"장덕동",
    hobby:"로아",
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
