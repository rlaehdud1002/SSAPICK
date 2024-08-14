import { useMutation, useQueryClient } from '@tanstack/react-query';
import { deleteFriend, postAddFriend } from 'api/friendApi';
import PlusDeleteButton from 'buttons/PlusDeleteButton';
import BaseImageIcon from 'icons/BaseImageIcon';
import { useState } from 'react';

interface FriendProps {
  campusSection: number;
  name: string;
  userId: number;
  profileImage?: string;
  follow: boolean;
  sameCampus: boolean;
  cohort: number;
}

const Friend = ({
  userId,
  name,
  cohort,
  campusSection,
  profileImage,
  sameCampus,
  follow,
}: FriendProps) => {
  const queryClient = useQueryClient();

  const [isPlus, setIsPlus] = useState<boolean>(true);

  const addMutation = useMutation({
    mutationKey: ['addFriend'],
    mutationFn: postAddFriend,

    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ['friends'],
      });
    },
  });

  const deleteMutation = useMutation({
    mutationKey: ['deleteFriend'],
    mutationFn: deleteFriend,
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ['friends'],
      });
    },
  });

  const onPlus = () => {
    isPlus ? setIsPlus(false) : setIsPlus(true);
  };

  return (
    <div className="flex flex-col">
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
        <div className="flex flex-col justify-start w-32">
          <span className="">
            {cohort}기 {campusSection}반 {name}
          </span>
          <div>
            {sameCampus && (
              <span className="text-xs bg-white/50 rounded-lg w-[56px] text-center px-1 py-1 mr-2">
                반 친구
              </span>
            )}
            {follow && (
              <span className="text-xs bg-white/50 rounded-lg w-[56px] px-1 py-1 text-center ">
                찐친
              </span>
            )}
          </div>
        </div>
        <div className="w-20">
          {follow ? (
            <div
              onClick={() => {
                deleteMutation.mutate(userId);
                onPlus();
              }}
            >
              <PlusDeleteButton title="언팔로우" />
            </div>
          ) : (
            <div
              onClick={() => {
                addMutation.mutate(userId);
                onPlus();
              }}
            >
              <PlusDeleteButton title="팔로우" isDelete={true} />
            </div>
          )}
        </div>
      </div>
      <div className="bg-white h-px w-90 mx-8 mt-5"></div>
    </div>
  );
};

export default Friend;
