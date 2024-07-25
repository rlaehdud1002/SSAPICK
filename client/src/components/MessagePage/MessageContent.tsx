import MessageQuestionIcon from 'icons/MessageQuestionIcon';
import UserPickIcon from 'icons/UserPickIcon';
import PointIcon from 'icons/PointIcon';

interface MessageContentProps {
  name: string;
  question: string;
  message: string;
  date: string;
  gen: string;
}

const MessageContent = ({
  name,
  question,
  message,
  date,
  gen,
}: MessageContentProps) => {
  return (
    <div className="mx-2 my-5 border-b-[1px]">
      <div className="flex flex-row justify-between">
        <div className='flex flex-row items-center'>
          <UserPickIcon gen={gen} width={32} height={32}/>
          <h1 className="ms-3">{name}</h1>
        </div>
        <PointIcon />
      </div>
      <div className="text-center text-gray-500 my-4">{question}</div>
      <div className="flex flex-row items-center bg-white/50 rounded-lg p-2">
        <MessageQuestionIcon className="me-2" />
        {message}
      </div>
      <div className="text-right my-2">
        <span className="text-gray-500 text-xs">{date}</span>
      </div>
    </div>
  );
};

export default MessageContent;
