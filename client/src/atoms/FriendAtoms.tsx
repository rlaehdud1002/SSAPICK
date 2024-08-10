import { IFriend } from 'atoms/Friend.type';
import { persistAtom } from 'atoms/RecoilPersist';
import { atom } from 'recoil';

// 전체 친구 리스트
export const friendListState = atom<IFriend[]>({
  key: 'friendListState',
  default: [],
});

// 친구 4명 리스트
export const pickFriendState = atom<IFriend[]>({
  key: 'pickFriendState',
  default: [],
  effects_UNSTABLE: [persistAtom],
});
