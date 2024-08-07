import { useQuery } from '@tanstack/react-query';
import { getReceivedMessage } from 'api/messageApi';
import { IMessage } from 'atoms/Message.type';
import MessageContent from 'components/MessagePage/MessageContent';
import NoMessage from 'components/MessagePage/NoMessage';

const Received = () => {
  const { data: messages = [], isLoading } = useQuery<IMessage[]>({
    queryKey: ['message', 'receive'],
    queryFn: getReceivedMessage,
  });

  return (
    <div>
      {!isLoading &&
        (messages.length > 0 ? (
          messages.map((message, index) => {
            return (
              <MessageContent key={index} message={message} status="receive" />
            );
          })
        ) : (
          <NoMessage content="받은 쪽지가 없습니다."/>
        ))}
    </div>
  );
};

export default Received;
