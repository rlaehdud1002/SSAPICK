import MessageContent from 'components/MessagePage/MessageContent';
import { Link, Outlet, useLocation } from 'react-router-dom';

const Message = () => {
  const location = useLocation().pathname.split('/')[2];
  return (
    <div className="m-5">
      <div className="flex flex-row justify-center">
        <Link
          to="/message/received"
          className={`border-b-2 w-1/2 text-center pb-3 text-lg ${location === 'received' || location === undefined ? 'text-color-5F86E9 border-[#5F86E9]' : 'text-white border-white'}`}
        >
          받은 쪽지
        </Link>
        <Link
          to="/message/send"
          className={`border-b-2 w-1/2 text-center pb-3 text-lg ${location === 'send' ? 'text-color-5F86E9 border-[#5F86E9]' : 'text-white border-white'}`}
        >
          보낸 쪽지
        </Link>
      </div>
      <div className="mt-5">
        <Outlet />
      </div>
    </div>
  );
};

export default Message;
