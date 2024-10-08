import { Popover, PopoverContent, PopoverTrigger } from 'components/ui/popover';
import PointIcon from 'icons/PointIcon';
import { useLocation } from 'react-router-dom';
import WarningDeleteModal from 'components/modals/WarningDeleteModal';
import { IMessage } from 'atoms/Message.type';
import { useState } from 'react';

interface WarningDeleteProps {
  message: IMessage;
}

const WarningDelete = ({ message }: WarningDeleteProps) => {
  const [open, setOpen] = useState<boolean>(false);

  let location = useLocation().pathname.split('/')[2];

  if (!location) {
    location = 'received';
  }

  return (
    <Popover
      open={open}
      onOpenChange={(open) => !open && setOpen((prev) => !prev)}
    >
      <PopoverTrigger onClick={() => setOpen(true)}>
        <PointIcon />
      </PopoverTrigger>
      <PopoverContent className="mr-4 w-[102px] rounded-lg bg-[#E9F2FD] flex flex-col justify-center">
        {!(location === 'send') && (
          <div>
            <WarningDeleteModal
              senderId={message.senderId}
              messageId={message.id}
              title="차단"
              message={message.content}
              location={location}
              setPopoverOpen={setOpen}
            />
            <div className="mb-4" />
          </div>
        )}
        <WarningDeleteModal
          senderId={message.senderId}
          messageId={message.id}
          title="삭제"
          location={location}
          message={message.content}
          setPopoverOpen={setOpen}
        />
      </PopoverContent>
    </Popover>
  );
};

export default WarningDelete;
