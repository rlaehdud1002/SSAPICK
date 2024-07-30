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
    image: '',
    name: '민준수',
    gender: '여자',
    th: 12,
    campus: '광주',
    mbti: 'ENFP',
    classNum: 2,
    major: '컴퓨터공학과',
    birth: '1995-01-07',
    town: '장덕동',
    hobby: '로아',
  },
});
