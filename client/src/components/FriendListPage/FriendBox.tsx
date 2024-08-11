import { useMutation, useQueryClient } from '@tanstack/react-query';
import { deleteFriend, postAddFriend } from 'api/friendApi';
import PlusDeleteButton from 'buttons/PlusDeleteButton';
import DeleteModal from 'components/modals/DeleteModal';
import BaseImageIcon from 'icons/BaseImageIcon';
import { useState } from 'react';

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

  // let isPlus = true;
  const [isPlus, setIsPlus] = useState<boolean>(true);

  const addMutation = useMutation({
    mutationKey: ["addFriend",],
    mutationFn: postAddFriend,
  
    onSuccess: () => {
      console.log("친구 추가 성공");
    },
  });
  
  const deleteMutation = useMutation({
    mutationKey: ["deleteFriend",],
    mutationFn: deleteFriend,
    
    onSuccess: () => {
      console.log("친구 삭제 성공");
    },
  });

  const onPlus = () => {
    {isPlus ? 
      (setIsPlus(false)) 
      : 
      (setIsPlus(true))}
  }

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
        {/* {userClass !== campusSection ? (
          <DeleteModal title="언팔로우" userId={userId} />
        ) : (
          <span></span>
        )}
        <DeleteModal title="언팔로우" userId={userId} />

        <button
          onClick={() => {
            mutation.mutate(userId);
          }}
        >
          팔로우
        </button> */}

        <div>
        {isPlus ? (
          <div onClick={()=>{
            addMutation.mutate(userId);
            onPlus();
            
          }}>
            <PlusDeleteButton title="팔로우" isDelete={true} />
          </div>
        ) : (
          <div onClick={
            ()=>{
              deleteMutation.mutate(userId);
              onPlus();
             
            }
          }>
            <PlusDeleteButton title="언팔로우" />
          </div>
        )}
      </div>
      </div>
      <div className="bg-white h-px w-90 mx-8 mt-5"></div>
    </div>
  );
};

export default Friend;
