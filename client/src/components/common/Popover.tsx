import DeleteModal from 'components/modals/DeleteModal';
import FriendBlockIcon from 'icons/FriendBlockIcon';
import PointIcon from 'icons/PointIcon';
import { Popover, PopoverContent, PopoverTrigger } from '../ui/popover';

interface PopOverProps {
  userId: number;
}

const PopOver = ({ userId }: PopOverProps) => {
  return (
    <Popover>
      <PopoverTrigger asChild>
        <div>
          <PointIcon />
        </div>
      </PopoverTrigger>
      <PopoverContent className="w-26 mr-5">
        <div className="flex items-center">
          <FriendBlockIcon width={22} height={22} />
          <DeleteModal title="언팔로우" userId={userId} />
          {userId}
        </div>
      </PopoverContent>
    </Popover>
  );
};

export default PopOver;
