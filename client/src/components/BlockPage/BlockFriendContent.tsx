import { useMutation, useQueryClient } from '@tanstack/react-query';
import { blockCancel } from 'api/blockApi';
import PlusDeleteButton from 'buttons/PlusDeleteButton';
import BaseImageIcon from 'icons/BaseImageIcon';

interface BlockFriendContentProps {
  campusName: string;
  campusSection: number;
  name: string;
  userId: number
  profileImage: string
}

const BlockFriendContent = ({
  campusName,
  campusSection,
  name,
  userId,
  profileImage
}: BlockFriendContentProps) => {
  const queryClient = useQueryClient();
  const mutation = useMutation({
    mutationKey: ['deleteBlock'],
    mutationFn: blockCancel,

    onSuccess: () => {
      console.log('차단 해제 성공');
      queryClient.invalidateQueries({
        queryKey: ['blocks'],
      });
    },
  });

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
          {campusName} {campusSection} {name}
        </div>
        <div
          onClick={() => {
            mutation.mutate(userId);
          }}
        >
          <PlusDeleteButton title="삭제" />
        </div>
      </div>
      {/* <Separator className="my-4 mx-4" />  */}
      <div className="bg-white h-px w-90 mx-8 mt-5"></div>
    </div>
  );
};

export default BlockFriendContent;
