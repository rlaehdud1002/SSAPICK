import { atom, selector } from 'recoil';
import { QuestionAtom, PickAtom } from './Pick.type';

export const questionState = atom<QuestionAtom>({
  key: 'questionState',
  default: {
    id: 1,
    banCount: 0,
    skipCount: 0,
    content: '테스트 질문 1',
    category: {
      id: 1,
      name: '프로젝트',
      thumbnail: '/icons/QuestionImage.png',
    },
    createdAt: '2024-07-26T16:26:05.0871814',
  },
});

export const pickState = atom<PickAtom[]>({
  key: 'pickState',
  default: [{
    id: 1,
    sender: {
      gender: 'M',
      campusName: '광주',
      campusSection: 1,
      campusDescription: '자바 전공',
    },
    receiver: {
      userId: 3,
      nickname: '받은 사람',
      gender: 'M',
      campusName: '광주',
      campusSection: 1,
      campusDescription: '자바 전공',
      profileImage: 'https://test-profile.com',
    },
    question: {
      id: 1,
      content: '테스트 질문 1',
      category: {
        id: 1,
        name: '프로젝트',
        thumbnail: '/icons/QuestionImage.png',
      },
      createdAt: '2024-07-26T16:26:05.0871814',
    },
    createdAt: '2024-07-26T16:26:05.1184801',
    messageSend: false,
  }],
});
