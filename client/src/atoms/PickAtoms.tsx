import { atom, selector } from 'recoil';
import { IQuestion, IPick } from './Pick.type';
import { persistAtom } from 'atoms/RecoilPersist';

export const questionState = atom<IQuestion[]>({
  key: 'questionState',
  default: [],
});

export const pickState = atom<IPick[]>({
  key: 'pickState',
  default: [],
});

export const pickCountState = atom<number>({
  key: 'pickCountState',
  default: 0,
  effects_UNSTABLE: [persistAtom]
});