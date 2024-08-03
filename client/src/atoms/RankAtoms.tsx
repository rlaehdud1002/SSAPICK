import { IRank } from 'atoms/Rank.type';
import { atom, selector } from 'recoil';

export const rankState = atom<IRank>({
  key: 'rankState',
});
