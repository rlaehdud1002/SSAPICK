import { atom, selector } from 'recoil';

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
})