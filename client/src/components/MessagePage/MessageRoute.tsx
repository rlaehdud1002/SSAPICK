import Received from 'components/MessagePage/Received';
import Send from 'components/MessagePage/Send';
import Message from 'pages/Message';
import { Route, Routes } from 'react-router-dom';

const MessageRoute = () => {
  return (
    <Routes>
      <Route path="/message" element={<Message />}>
        <Route path="received" element={<Received />} />
        <Route path="send" element={<Send />} />
      </Route>
    </Routes>
  );
};

export default MessageRoute;
