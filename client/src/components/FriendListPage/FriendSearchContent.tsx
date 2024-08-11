import { Separator } from "components/ui/separator";
import PlusDeleteButton from "buttons/PlusDeleteButton";
import { Fragment, useState } from "react";
import { useMutation, useQuery } from "@tanstack/react-query";
import { deleteFriend, postAddFriend } from "api/friendApi";

interface FriendSearchContentProps {
  cohort: number;
  classSection: number;
  name: string;
  userid?: number;
  profileImage?: string;
}

const FriendSearchContent = ({ name, cohort, classSection,userid, profileImage }: FriendSearchContentProps) => {
  const [isPlus, setIsPlus] = useState<boolean>(true);
  
  // 친구 추가
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

  return <Fragment>
    <div className="flex items-center mt-5 justify-between mx-8">
      <div>
        <img className="w-14 h-14 rounded-full" src={profileImage} alt="profile" />
      </div>
      <div className=""> {cohort}기 {classSection}반 {name}</div>
      <div>
        {isPlus ? (
          <div onClick={()=>{
            addMutation.mutate(0);
            onPlus();
          }}>
            <PlusDeleteButton title="팔로우" isDelete={true} />
          </div>
        ) : (
          <div onClick={
            ()=>{
              deleteMutation.mutate(0);
              onPlus();
            }
          }>
            <PlusDeleteButton title="언팔로우" />
          </div>
        )}
      </div>
    </div>
    <Separator className="my-4 mx-4" />  
    {/* <div className="bg-white h-px w-90 mx-8 mt-5"></div> */}
  </Fragment>
}

export default FriendSearchContent;
