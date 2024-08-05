import PlusDeleteButton from "buttons/PlusDeleteButton";
import { Separator } from "@radix-ui/react-select";

interface BlockFriendContentProps {
    campusName:string;
    campusSection:number;
    name:string
}

const BlockFriendContent = ({campusName,campusSection,name}:BlockFriendContentProps) => {

  const onEvnet = () => {
    console.log("차단리스트에서 친구 삭제")
  }
    return (
        <div>
        <div className="flex items-center mt-5 justify-between mx-8">
      <div>
        <img className="w-14 h-14" src="/icons/Profile.png" alt="profile" />
      </div>
      <div className="">{campusName} {campusSection} {name}</div>
        <div onClick={onEvnet}>
        <PlusDeleteButton title="삭제" />
        </div>
    </div>
    {/* <Separator className="my-4 mx-4" />  */}
    <div className="bg-white h-px w-90 mx-8 mt-5"></div>
    </div>
    )
}

export default BlockFriendContent