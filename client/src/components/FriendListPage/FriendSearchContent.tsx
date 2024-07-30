import { Separator } from "components/ui/separator";
import PlusDeleteButton from "buttons/PlusDeleteButton";
import { Fragment, useState } from "react";

interface FriendSearchContentProps {
  campus: string;
  th: number;
  classNum: number;
  name: string;
}

const FriendSearchContent = ({ campus, th, classNum, name }: FriendSearchContentProps) => {
  const [isPlus, setIsPlus] = useState<boolean>(true);
  const onPlus = () => {
    isPlus ? setIsPlus(false) : setIsPlus(true);
  }

  return <Fragment>
    <div className="flex items-center mt-5 justify-between mx-8">
      <div>
        <img className="w-14 h-14" src="../icons/Profile.png" alt="profile" />
      </div>
      <div className="">{campus}캠퍼스 {th}기 {classNum}반 {name}</div>
      <div>
        {isPlus ? (
          <div onClick={onPlus}>
            <PlusDeleteButton title="추가" isDlete={true} />
          </div>
        ) : (
          <div onClick={onPlus}>
            <PlusDeleteButton title="삭제" />
          </div>
        )}
      </div>
    </div>
    <Separator className="my-4 mx-4" />  
    {/* <div className="bg-white h-px w-90 mx-8 mt-5"></div> */}
  </Fragment>
}

export default FriendSearchContent;