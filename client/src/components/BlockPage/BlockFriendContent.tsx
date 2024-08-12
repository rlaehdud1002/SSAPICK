import { useMutation, useQueryClient } from '@tanstack/react-query';
import { blockCancel } from 'api/blockApi';
import PlusDeleteButton from 'buttons/PlusDeleteButton';
import WarningDelete from 'components/common/WarningDelete';
import BlockCancelModal from 'components/modals/BlockCancelModal';
import BaseImageIcon from 'icons/BaseImageIcon';

interface BlockFriendContentProps {
  campusName: string;
  campusSection: number;
  name: string;
  userId: number;
  profileImage: string;
  cohort: number;
}

const BlockFriendContent = ({
  campusName,
  campusSection,
  name,
  userId,
  profileImage,
  cohort,
}: BlockFriendContentProps) => {
  

  return (
    <div>
      <div className="flex items-center mt-5 justify-between mx-8">
        <div>
          {profileImage ? (
            <img
              src={profileImage}
              alt="profileImage"
              className="w-[55px] h-[55px] rounded-full"
            />
          ) : (
            <BaseImageIcon width={64} height={64} />
          )}
        </div>
        <div>
          {cohort}기 {campusSection}반 {name}
        </div>
          <BlockCancelModal Id={userId} category='user'/>
      </div>
      {/* <Separator className="my-4 mx-4" />  */}
      <div className="bg-white h-px w-90 mx-8 mt-5"></div>
    </div>
  );
};

export default BlockFriendContent;
