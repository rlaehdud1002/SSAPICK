import { useQuery } from "@tanstack/react-query";
import BlockFriendContent from "./BlockFriendContent"
import { getBlockList } from "api/blockApi";
import { IBlock } from "atoms/Block.type";

const BlockFriend = () => {
    const { data: blocks } = useQuery<IBlock[]>({
        queryKey: ['blocks'],
        queryFn: async () => await getBlockList(),
      });
    return (
        <div className="mb-20">
            {blocks &&
            blocks.map((block, index) => (
                <BlockFriendContent key={index} campusName={block.campusName} campusSection={block.campusSection} name={block.nickname}/>
            ))}
        </div>
    )
}

export default BlockFriend