import { IMessage } from 'atoms/Message.type';
import WarningDelete from 'components/common/WarningDelete';
import BaseImageIcon from 'icons/BaseImageIcon';
import MessageQuestionIcon from 'icons/MessageQuestionIcon';
import UserPickIcon from 'icons/UserPickIcon';
interface MessageContentProps {
  message: IMessage;
  status: string;
}

const MessageContent = ({ message, status }: MessageContentProps) => {
  const createTime = new Date(message.createdAt).getTime();
  const currentTime = new Date().getTime();
  const diffTime = currentTime - createTime;

  const months = String(
    Math.floor((diffTime / (1000 * 60 * 60 * 24 * 30)) % 12),
  );
  const days = String(Math.floor((diffTime / (1000 * 60 * 60 * 24)) % 30));
  const hours = String(Math.floor((diffTime / (1000 * 60 * 60)) % 24));
  const minutes = String(Math.floor((diffTime / (1000 * 60)) % 60));

  let receivedTime;

  if (months !== '0') {
    receivedTime = `${months}개월 전`;
  } else if (days !== '0') {
    receivedTime = `${days}일 전`;
  } else if (hours !== '0') {
    receivedTime = `${hours}시간 전`;
  } else if (minutes !== '0') {
    receivedTime = `${minutes}분 전`;
  } else {
    receivedTime = '방금';
  }

  return (
    <div className="mx-2 mt-5 mb-4 pb-3 border-b-[1px]">
      <div className="flex flex-row justify-between">
        <div className="flex flex-row items-center">
          {status === 'send' ? (
            <UserPickIcon gen={message.receiverGender} width={32} height={32} />
          ) : message.senderProfileImage ? (
            <img
              src={message.senderProfileImage}
              alt="noProfileImage"
              className="rounded-full w-8 h-8"
            />
          ) : (
            <BaseImageIcon width={32} height={32} />
          )}
          <h1 className="ms-3">
            {status === 'send'
              ? `${message.receiverCampus}캠퍼스 ${message.receiverSection}반`
              : message.senderName}
          </h1>
        </div>
        <div className="flex flex-row items-center">
          <span className="text-gray-500 text-xs">{receivedTime}</span>
          <WarningDelete message={message} />
        </div>
      </div>
      <div className="text-center text-gray-500 my-4 flex flex-row items-center justify-center">
        <h1 className="ml-3 mr-3 luckiest_guy text-lg">Q</h1>
        {message.questionContent}
      </div>
      <div className="flex flex-row items-center bg-white/50 rounded-lg p-2">
        <MessageQuestionIcon className="me-2" />
        {message.content}
      </div>
    </div>
  );
};

export default MessageContent;
