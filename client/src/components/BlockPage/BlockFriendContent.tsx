import { useMutation } from "@tanstack/react-query";
import { blockCancel } from "api/blockApi";
import PlusDeleteButton from "buttons/PlusDeleteButton";

interface BlockFriendContentProps {
  campusName: string;
  campusSection: number;
  name: string;
  userId: number;
  
}

const BlockFriendContent = ({ campusName, campusSection, name, userId }: BlockFriendContentProps) => {

  const mutation = useMutation({
    mutationKey: ['deleteBlock'],
    mutationFn: blockCancel,

    onSuccess: () => {
      console.log('차단 해제 성공');
    },
  });

  return (
    <div>
      <div className="flex items-center mt-5 justify-between mx-8">
        <div>
          <img className="w-14 h-14" src="/icons/Profile.png" alt="profile" />
        </div>
        <div className="">{campusName} {campusSection} {name}</div>
        <div onClick={()=>{mutation.mutate(userId)}}>
          <PlusDeleteButton title="삭제"/>
        </div>
      </div>
      {/* <Separator className="my-4 mx-4" />  */}
      <div className="bg-white h-px w-90 mx-8 mt-5"></div>
    </div>
  )
}

export default BlockFriendContent