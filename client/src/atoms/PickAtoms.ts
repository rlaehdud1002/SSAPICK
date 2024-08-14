import { atom } from 'recoil';
import { IQuestion } from './Pick.type';
import { persistAtom } from 'atoms/RecoilPersist';

// 질문 리스트
export const questionState = atom<IQuestion[]>({
  key: 'questionState',
  default: [],
  effects_UNSTABLE: [persistAtom],
});

// 질문 업데이트 여부
export const isQuestionUpdatedState = atom<boolean>({
  key: 'isQuestionUpdatedState',
  default: false,
  effects_UNSTABLE: [persistAtom],
});
