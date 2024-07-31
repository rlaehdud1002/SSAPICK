import MessageContent from 'components/MessagePage/MessageContent';

import { messageState } from 'atoms/MessageAtoms';
import { useRecoilValue } from 'recoil';

const Received = () => {
  const message = useRecoilValue(messageState);
  console.log(message.createdAt.slice(0, 10))
  return (
    <div>
      <MessageContent
        name={message.senderName}
        question={message.questionContent}
        message={message.content}
        date={message.createdAt.slice(0, 10)}
        gen="female"
      />
      <MessageContent
        name={message.senderName}
        question={message.questionContent}
        message={message.content}
        date={message.createdAt.slice(0, 10)}
        gen="male"
      />
    </div>
  );
};

export default Received;
