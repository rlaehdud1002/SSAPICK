import { useQuery } from '@tanstack/react-query';
import { getReceivedMessage } from 'api/messageApi';
import { IMessage } from 'atoms/Message.type';
import MessageContent from 'components/MessagePage/MessageContent';

const Received = () => {
  const { data: messages, isLoading } = useQuery<IMessage[]>({
    queryKey: ['message', 'receive'],
    queryFn: getReceivedMessage,
  });

  return (
    <div>
      {messages &&
        messages.map((message, index) => {
          return (
            <MessageContent
              key={index}
              id={message.id}
              name={message.senderName}
              question={message.questionContent}
              message={message.content}
              date={message.createdAt.slice(0, 10)}
              gen={message.senderGender}
            />
          );
        })}
    </div>
  );
};

export default Received;
