import PlusDeleteButton from "buttons/PlusDeleteButton";
import { useState } from "react";

interface FriendSearchContentProps {
  campus: string;
  th: number;
  classNum: number;
  name: string;
}

const FriendSearchContent = ({campus, th, classNum, name}:FriendSearchContentProps) => {
  const [isPlus, setIsPlus] = useState<boolean>(true);
  const onPlus = () => {
    isPlus ? setIsPlus(false) : setIsPlus(true);
  }

  return <div>
    <div>
    <div className="flex items-center mt-5 ml-10 mr-10 justify-between">
      <div>
      <img className="w-16 h-16 ml-6" src="../icons/Profile.png" alt="profile" />
      </div>
      <div className="">{campus}캠퍼스 {th}기 {classNum}반 {name}</div>
      <div>
        {isPlus ?(
          <div onClick={onPlus}>
        <PlusDeleteButton title="추가" isDlete={true}/>
        </div>
        ):(
          <div onClick={onPlus}>
        <PlusDeleteButton title="삭제" /> 
        </div>
        )}
      </div>
    </div>
    <div className="bg-white h-px w-90 mx-8 mt-5"></div>
  </div>
  </div>
}

export default FriendSearchContent;