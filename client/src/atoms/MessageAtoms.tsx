import { atom, selector } from 'recoil';
import { IMessage } from './Message.type';

export const messageState = atom<IMessage[]>({
  key: 'messageState',
  default: [
    {
      id: 1,
      senderName: '보낸 사람',
      receiverName: '받은 사람',
      createdAt: '2024-07-26T16:26:04.4632027',
      content: '테스트 메시지 1',
      questionContent: '테스트 질문',
    },
  ],
});
