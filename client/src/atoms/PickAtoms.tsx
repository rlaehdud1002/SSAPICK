import { atom, selector } from 'recoil';
import { IQuestion } from './Pick.type';

// 질문 리스트
export const questionState = atom<IQuestion[]>({
  key: 'questionState',
  default: [],
});

// 쿨타임 끝나는 시간
export const endCoolTimeState = atom<number>({
  key: 'endCoolTimeState',
  default: 0,
});

// 질문 업데이트 여부
export const isQuestionUpdatedState = atom<boolean>({
  key: 'isQuestionUpdatedState',
  default: false,
});
