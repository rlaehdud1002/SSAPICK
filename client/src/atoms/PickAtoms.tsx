import { atom, selector } from 'recoil';
import { IQuestion, IPick } from './Pick.type';

// 질문 리스트
export const questionState = atom<IQuestion[]>({
  key: 'questionState',
  default: [],
});

// pick + block count
export const pickCountState = atom<number>({
  key: 'pickCountState',
  default: 0,
});

// 질문을 가리키는 인덱스
export const questionIndexState = atom<number>({
  key: 'questionIndexState',
  default: 0,
});

// block + pass count
export const blockPassCountState = atom<number>({
  key: 'blockPassCountState',
  default: 0,
});
