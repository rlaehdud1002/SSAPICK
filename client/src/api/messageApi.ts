import instance from 'api/clientApi';
import { IMessage, ISendMessage } from 'atoms/Message.type';
import { IPaging } from 'atoms/Pick.type';
import { BaseResponse } from 'atoms/User.type';

// 메시지 보내기
export const postMessageSend = async (
  messageData: ISendMessage,
): Promise<void> => {
  const {
    data: { success },
  } = await instance.post<BaseResponse<null>>('/message', messageData);

  if (!success) {
    throw new Error('메시지 전송 실패');
  }

  console.log('postMessageSend');
};

// 받은 메시지 조회
export const getReceivedMessage = async (): Promise<IPaging<IMessage[]>> => {
  const {
    data: { success, data },
  } = await instance.get<BaseResponse<IPaging<IMessage[]>>>('/message/receive');

  if (!success) {
    throw new Error('받은 메시지 조회 실패');
  }

  return data;
};

// 보낸 메시지 조회
export const getSendMessage = async (): Promise<IPaging<IMessage[]>> => {
  const {
    data: { success, data },
  } = await instance.get<BaseResponse<IPaging<IMessage[]>>>('/message/send');

  if (!success) {
    throw new Error('보낸 메시지 조회 실패');
  }

  return data;
};

// 받은 메시지 삭제
export const deleteReceivedMessage = async (
  messageId: number,
): Promise<void> => {
  const {
    data: { success },
  } = await instance.delete<BaseResponse<null>>(
    `/message/${messageId}/receive`,
  );

  if (!success) {
    throw new Error('받은 메시지 삭제 실패');
  }

  console.log('deleteReceivedMessage');
};

// 보낸 메시지 삭제
export const deleteSendMessage = async (messageId: number): Promise<void> => {
  const {
    data: { success },
  } = await instance.delete<BaseResponse<null>>(`/message/${messageId}/send`);

  if (!success) {
    throw new Error('보낸 메시지 삭제 실패');
  }

  console.log('deleteSendMessage');
};
