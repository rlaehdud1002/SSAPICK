import { Popover, PopoverContent, PopoverTrigger } from 'components/ui/popover';

import PointIcon from 'icons/PointIcon';
import { useLocation } from 'react-router-dom';
import WarningDeleteModal from 'components/modals/WarningDeleteModal';

const WarningDelete = () => {
  const location = useLocation().pathname.split('/')[2];
  return (
    <Popover>
      <PopoverTrigger>
        <PointIcon />
      </PopoverTrigger>
      <PopoverContent className="mr-4 w-[102px] rounded-lg bg-[#E9F2FD] flex flex-col justify-center">
        {!(location === 'send') && (
          <WarningDeleteModal
            messageId={1}
            title="신고"
            message="쪽지 내용"
            location={location}
          />
        )}
        <WarningDeleteModal messageId={1} title="삭제" location={location} />
      </PopoverContent>
    </Popover>
  );
};

export default WarningDelete;
