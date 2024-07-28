import PlusDeleteButton from "buttons/PlusDeleteButton";
import { useState } from "react";
import { Separator } from "@radix-ui/react-select";



interface BlockFriendContentProps {
    th:number;
    classNum:number;
    name:string
}

const BlockFriendContent = ({th,classNum,name}:BlockFriendContentProps) => {

    const [isPlus, setIsPlus] = useState<boolean>(true);
  const onEvnet = () => {
    console.log("차단리스트에서 친구 삭제")
  }
    return (
        <div>
        <div className="flex items-center mt-5 justify-between mx-8">
      <div>
        <img className="w-14 h-14" src="/icons/Profile.png" alt="profile" />
      </div>
      <div className="">{th}기 {classNum}반 {name}</div>
        <div onClick={onEvnet}>
        <PlusDeleteButton title="삭제" />
        </div>
    </div>
    <Separator className="my-4 mx-4" /> 
    </div>
    )
}

export default BlockFriendContent