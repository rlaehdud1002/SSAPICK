import MessageContent from 'components/MessagePage/MessageContent';

import { messageState } from 'atoms/MessageAtoms';
import { useRecoilValue } from 'recoil';

const Received = () => {
  const messages = useRecoilValue(messageState);
  return (
    <div>
      {messages.map((message, index) => {
        return (
          <MessageContent
            name={message.senderName}
            question={message.questionContent}
            message={message.content}
            date={message.createdAt.slice(0, 10)}
            gen="F"
          />
        );
      })}
    </div>
  );
};

export default Received;
