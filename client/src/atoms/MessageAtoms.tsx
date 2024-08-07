import { atom, selector } from 'recoil';
import { IMessage } from './Message.type';

export const messageState = atom<IMessage[]>({
  key: 'messageState',
  default: [],
});
