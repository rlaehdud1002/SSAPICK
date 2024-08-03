import MessageContent from 'components/MessagePage/MessageContent';

import { useQuery } from '@tanstack/react-query';
import { IMessage } from 'atoms/Message.type';
import { getSendMessage } from 'api/messageApi';

const Send = () => {
  const { data: messages, isLoading } = useQuery<IMessage[]>({
    queryKey: ['message', 'receive'],
    queryFn: getSendMessage,
  });

  return (
    <div>
      {messages &&
        messages.map((message, index) => {
          return (
            <MessageContent
              key={index}
              id={message.id}
              name={message.receiverName}
              question={message.questionContent}
              message={message.content}
              date={message.createdAt.slice(0, 10)}
              gen={message.receiverGender}
            />
          );
        })}
    </div>
  );
};

export default Send;
