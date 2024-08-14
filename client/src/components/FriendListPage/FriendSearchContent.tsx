import { useMutation, useQueryClient } from '@tanstack/react-query';
import { deleteFriend, postAddFriend } from 'api/friendApi';
import PlusDeleteButton from 'buttons/PlusDeleteButton';
import { Separator } from 'components/ui/separator';
import BaseImageIcon from 'icons/BaseImageIcon';
import { Fragment, useEffect, useState } from 'react';

interface FriendSearchContentProps {
  cohort: number;
  classSection: number;
  name: string;
  userId: number;
  profileImage?: string;
  follow?: boolean;
}

const FriendSearchContent = ({
  name,
  follow,
  cohort,
  classSection,
  userId,
  profileImage,
}: FriendSearchContentProps) => {
  const [isPlus, setIsPlus] = useState<boolean>(!follow);
  const queryClient = useQueryClient();

  // 친구 추가
  const addMutation = useMutation({
    mutationKey: ['addFriend'],
    mutationFn: postAddFriend,

    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ['friends'],
      });
      setIsPlus(false);
    },
  });

  const deleteMutation = useMutation({
    mutationKey: ['deleteFriend'],
    mutationFn: deleteFriend,

    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ['friends'],
      });
      setIsPlus(true);
    },
  });
  useEffect(() => {
    // follow 상태가 변경될 때 isPlus 상태를 업데이트합니다.
    setIsPlus(!follow);
  }, [follow]);

  const onPlus = () => {
    isPlus ? setIsPlus(false) : setIsPlus(true);
  };

  return (
    <Fragment>
      <div className="flex items-center mt-5 justify-between mx-8">
        <div>
          {profileImage ? (
            <img
              className=" w-16 h-16 rounded-full"
              src={profileImage}
              alt="profile"
            />
          ) : (
            <BaseImageIcon width={64} height={64} />
          )}
        </div>

        <div className="flex justify-start w-32">
          {cohort}기 {classSection}반 {name}
        </div>
        <div className="w-20">
          {isPlus ? (
            <div
              onClick={() => {
                onPlus();
                addMutation.mutate(userId);
              }}
            >
              <PlusDeleteButton title="팔로우" isDelete={true} />
            </div>
          ) : (
            <div
              onClick={() => {
                onPlus();
                deleteMutation.mutate(userId);
              }}
            >
              <PlusDeleteButton title="언팔로우" />
            </div>
          )}
        </div>
      </div>
      <Separator className="my-4 mx-4" />
    </Fragment>
  );
};

export default FriendSearchContent;
