import instance from 'api/clientApi';
import { IMessage, ISendMessage } from 'atoms/Message.type';
import { IPaging } from 'atoms/Pick.type';
import { BaseResponse, IEmpty } from 'atoms/User.type';

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
};

// 받은 메시지 조회
export const getReceivedMessage = async (
  page: number,
  size: number,
): Promise<IPaging<IMessage[]>> => {
  const {
    data: { success, data },
  } = await instance.get<BaseResponse<IPaging<IMessage[]>>>(
    `/message/receive?page=${page}&size=${size}`,
  );

  if (!success) {
    throw new Error('받은 메시지 조회 실패');
  }

  return data;
};

// 보낸 메시지 조회
export const getSendMessage = async (
  page: number,
  size: number,
): Promise<IPaging<IMessage[]>> => {
  const {
    data: { success, data },
  } = await instance.get<BaseResponse<IPaging<IMessage[]>>>(
    `/message/send?page=${page}&size=${size}`,
  );

  if (!success) {
    throw new Error('보낸 메시지 조회 실패');
  }

  return data;
};

// 받은 메시지 삭제
export const deleteReceivedMessage = async (
  messageId: number,
): Promise<void> => {
  const response = await instance.delete<BaseResponse<IEmpty>>(
    `/message/${messageId}/receive`,
  );

  if (response.status !== 204) {
    throw new Error('받은 메시지 삭제 실패');
  }
};

// 보낸 메시지 삭제
export const deleteSendMessage = async (messageId: number): Promise<void> => {
  const response = await instance.delete<BaseResponse<IEmpty>>(
    `/message/${messageId}/send`,
  );

  if (response.status !== 204) {
    throw new Error('보낸 메시지 삭제 실패');
  }
};
