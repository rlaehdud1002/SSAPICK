import MessageContent from 'components/MessagePage/MessageContent';

import { useQuery } from '@tanstack/react-query';
import { IMessage } from 'atoms/Message.type';
import { getSendMessage } from 'api/messageApi';
import NoMessage from 'components/MessagePage/NoMessage';

const Send = () => {
  const { data: messages = [], isLoading } = useQuery<IMessage[]>({
    queryKey: ['message', 'send'],
    queryFn: getSendMessage,
  });

  return (
    <div>
      {!isLoading &&
        (messages.length > 0 ? (
          messages.map((message, index) => {
            return (
              <MessageContent key={index} message={message} status="send" />
            );
          })
        ) : (
          <NoMessage content="보낸 쪽지가 없습니다."/>
        ))}
    </div>
  );
};

export default Send;
