import { Popover, PopoverContent, PopoverTrigger } from 'components/ui/popover';

import PointIcon from 'icons/PointIcon';
import { useLocation } from 'react-router-dom';
import WarningDeleteModal from 'components/modals/WarningDeleteModal';

const WarningDelete = () => {
  const location = useLocation().pathname.split('/')[2];
  console.log(location);
  return (
    <Popover>
      <PopoverTrigger>
        <PointIcon />
      </PopoverTrigger>
      <PopoverContent className="me-4 w-28 rounded-xl bg-[#E9F2FD]">
        {!(location === 'send') && (
          <WarningDeleteModal
            title="신고"
            message="쪽지 내용"
          />
        )}
        <WarningDeleteModal title="삭제" />
      </PopoverContent>
    </Popover>
  );
};

export default WarningDelete;
