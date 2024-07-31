import MessageContent from 'components/MessagePage/MessageContent';

import { messageState } from 'atoms/messageAtoms';
import { useRecoilValue } from 'recoil';

const Received = () => {
  const message = useRecoilValue(messageState);
  return (
    <div>
      <MessageContent
        name={message.senderName}
        question={message.questionContent}
        message={message.content}
        date={message.createdAt.slice(0, 10)}
        gen="F"
      />
      <MessageContent
        name={message.senderName}
        question={message.questionContent}
        message={message.content}
        date={message.createdAt.slice(0, 10)}
        gen="M"
      />
    </div>
  );
};

export default Received;
