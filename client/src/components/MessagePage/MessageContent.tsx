import { IMessage } from 'atoms/Message.type';
import WarningDelete from 'components/common/WarningDelete';
import MessageQuestionIcon from 'icons/MessageQuestionIcon';
import UserPickIcon from 'icons/UserPickIcon';
interface MessageContentProps {
  message: IMessage;
  status: string;
}

const MessageContent = ({ message, status }: MessageContentProps) => {
  return (
    <div className="mx-2 my-5 border-b-[1px]">
      <div className="flex flex-row justify-between">
        <div className="flex flex-row items-center">
          <UserPickIcon gen={status === 'send' ? message.senderGender : message.receiverGender} width={32} height={32} />
          <h1 className="ms-3">{status === 'send' ? message.receiverName : "익명"}</h1>
        </div>
        <WarningDelete message={message}/>
      </div>
      <div className="text-center text-gray-500 my-4">{message.questionContent}</div>
      <div className="flex flex-row items-center bg-white/50 rounded-lg p-2">
        <MessageQuestionIcon className="me-2" />
        {message.content}
      </div>
      <div className="text-right my-2">
        <span className="text-gray-500 text-xs">{message.createdAt.slice(0, 10)}</span>
      </div>
    </div>
  );
};

export default MessageContent;
