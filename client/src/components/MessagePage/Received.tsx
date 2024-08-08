import { useQuery } from '@tanstack/react-query';
import { getReceivedMessage } from 'api/messageApi';
import { IMessage } from 'atoms/Message.type';
import { IPaging } from 'atoms/Pick.type';
import MessageContent from 'components/MessagePage/MessageContent';
import NoMessage from 'components/MessagePage/NoMessage';

const Received = () => {
  const { data: messages, isLoading } = useQuery<IPaging<IMessage[]>>({
    queryKey: ['receiveMessage'],
    queryFn: getReceivedMessage,
  });

  if (isLoading || !messages) {
    return <div>Loading...</div>;
  }

  return (
    <div>
      {messages.content.length > 0 ? (
        messages.content.map((message, index) => {
          return (
            <MessageContent key={index} message={message} status="receive" />
          );
        })
      ) : (
        <NoMessage content="받은 쪽지가 없습니다." />
      )}
    </div>
  );
};

export default Received;
