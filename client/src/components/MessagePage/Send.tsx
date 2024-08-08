import MessageContent from 'components/MessagePage/MessageContent';

import { useQuery } from '@tanstack/react-query';
import { IMessage } from 'atoms/Message.type';
import { getSendMessage } from 'api/messageApi';
import NoMessage from 'components/MessagePage/NoMessage';
import { IPaging } from 'atoms/Pick.type';

const Send = () => {
  const { data: messages, isLoading } = useQuery<IPaging<IMessage[]>>({
    queryKey: ['sendMessage'],
    queryFn: getSendMessage,
  });

  if (isLoading || !messages) {
    return <div>Loading...</div>;
  }

  return (
    <div>
      {messages.content.length > 0 ? (
        messages.content.map((message, index) => {
          return <MessageContent key={index} message={message} status="send" />;
        })
      ) : (
        <NoMessage content="보낸 쪽지가 없습니다." />
      )}
    </div>
  );
};

export default Send;
