import { Popover, PopoverContent, PopoverTrigger } from 'components/ui/popover';

import PointIcon from 'icons/PointIcon';
import WarningIcon from 'icons/WarningIcon';
import DeleteIcon from 'icons/DeleteIcon';

const WarningDelete = () => {
  return (
    <Popover>
      <PopoverTrigger>
        <PointIcon />
      </PopoverTrigger>
      <PopoverContent className="me-4 w-28 rounded-xl bg-[#E9F2FD]">
        <div className="flex flex-row">
          <WarningIcon width={24} height={24} className="me-3" />
          <span>신고</span>
        </div>
        <div className="flex flex-row">
          <DeleteIcon width={24} height={24} className="me-3" />
          <span>삭제</span>
        </div>
      </PopoverContent>
    </Popover>
  );
};

export default WarningDelete;
