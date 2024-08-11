import { Popover, PopoverContent, PopoverTrigger } from 'components/ui/popover';
import PointIcon from 'icons/PointIcon';
import { useLocation } from 'react-router-dom';
import WarningDeleteModal from 'components/modals/WarningDeleteModal';
import { IMessage } from 'atoms/Message.type';

interface WarningDeleteProps {
  message: IMessage;
}

const WarningDelete = ({ message }: WarningDeleteProps) => {
  let location = useLocation().pathname.split('/')[2];

  if (!location) {
    location = 'received';
  }

  return (
    <Popover>
      <PopoverTrigger>
        <PointIcon />
      </PopoverTrigger>
      <PopoverContent className="mr-4 w-[102px] rounded-lg bg-[#E9F2FD] flex flex-col justify-center">
        {!(location === 'send') && (
          <WarningDeleteModal
            senderId={message.senderId}
            messageId={message.id}
            title="신고"
            message={message.content}
            location={location}
          />
        )}
        <WarningDeleteModal
          senderId={message.senderId}
          messageId={message.id}
          title="삭제"
          location={location}
          message={message.content}
        />
      </PopoverContent>
    </Popover>
  );
};

export default WarningDelete;
