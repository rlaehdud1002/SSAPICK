import { IFriend } from 'atoms/Friend.type';
import { atom } from 'recoil';

export const friendListState = atom<IFriend[]>({
  key: 'friendListState',
  default: [],
});
