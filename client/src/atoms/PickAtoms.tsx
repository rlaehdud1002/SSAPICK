import { atom, selector } from 'recoil';
import { IQuestion, IPick } from './Pick.type';

export const questionState = atom<IQuestion[]>({
  key: 'questionState',
  default: [],
});

export const pickState = atom<IPick[]>({
  key: 'pickState',
  default: [],
});
