import MessageContent from 'components/MessagePage/MessageContent';
import Received from 'components/MessagePage/Received';
import Send from 'components/MessagePage/Send';
import { Link, Outlet, Route, Routes } from 'react-router-dom';

const Message = () => {
  return (
    <div className="m-5">
      <div className="flex flex-row justify-center">
        <Link
          to="/message/received"
          className="border-b-2 w-1/2 text-center text-white pb-3 text-lg"
        >
          받은 쪽지
        </Link>
        <Link
          to="/message/send"
          className="border-b-2 w-1/2 text-center text-white pb-3 text-lg"
        >
          보낸 쪽지
        </Link>
      </div>
      {/* <div className="mt-5">
        <Outlet />
      </div> */}
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

export default Message;
