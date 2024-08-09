import { useMutation, useQueryClient } from '@tanstack/react-query';
import { postAddFriend } from 'api/friendApi';
import DeleteModal from 'components/modals/DeleteModal';
import BaseImageIcon from 'icons/BaseImageIcon';

interface FriendProps {
  campus: string;
  campusSection: number;
  name: string;
  campusDescription: string;
  userId: number;
  userClass?: number;
  profileImage?: string;
}

const Friend = ({
  campus,
  userId,
  name,
  campusSection,
  userClass,
  profileImage,
}: FriendProps) => {
  const queryClient = useQueryClient();

  const mutation = useMutation({
    mutationKey: ['friends', 'follow'],
    mutationFn: postAddFriend,
    onSuccess: () => {
      console.log('친구 팔로우 성공');
      queryClient.invalidateQueries({
        queryKey: ['friends'],
      });
    },
  });

  return (
    <div className="flex flex-col relative">
      <div className="flex items-center ml-5 mr-5 justify-between">
        {profileImage ? (
          <img
            className="w-16 h-16 ml-6 rounded-full"
            src={profileImage}
            alt="profile"
          />
        ) : (
          <BaseImageIcon width={64} height={64} className="ml-6" />
        )}

        <div className="flex flex-col">
          <span>
            {campus}캠퍼스 {campusSection}반 {name}
          </span>
          <div className="flex flex-row">
            <span className="text-xs bg-white/50 rounded-lg w-[56px] text-center mt-1 mr-2">
              반 친구
            </span>
            <span className="text-xs bg-white/50 rounded-lg w-[56px] text-center mt-1">
              찐친
            </span>
          </div>
        </div>
        {userClass !== campusSection ? (
          <DeleteModal title="언팔로우" userId={userId} />
        ) : (
          <span></span>
        )}
        {/* <div>{userId}</div> */}
        {/* <DeleteModal title="언팔로우" userId={userId} /> */}
        <DeleteModal title="언팔로우" userId={userId} />
        <button
          onClick={() => {
            mutation.mutate(userId);
          }}
        >
          팔로우
        </button>
      </div>
      <div className="bg-white h-px w-90 mx-8 mt-5"></div>
    </div>
  );
};

export default Friend;
