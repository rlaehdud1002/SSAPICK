import MessageContent from 'components/MessagePage/MessageContent';

const Received = () => {
  return (
    <div>
      <MessageContent
        name="김도영"
        question="나랑 같이 프로젝트 하고 싶은 사람은?"
        message="쪽지 내용"
        date="2024.07.23"
        gen="female"
      />
      <MessageContent
        name="민준수"
        question="나랑 같이 프로젝트 하고 싶은 사람은?"
        message="쪽지 내용"
        date="2024.07.23"
        gen="male"
      />
    </div>
  );
};

export default Received;
