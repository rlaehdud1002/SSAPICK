import { atom, selector } from 'recoil';
import { IQuestion } from './Pick.type';

// 질문 리스트
export const questionState = atom<IQuestion[]>({
  key: 'questionState',
  default: [],
});
