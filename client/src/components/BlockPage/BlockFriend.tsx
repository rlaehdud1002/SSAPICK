import { useMutation, useQuery } from "@tanstack/react-query";
import BlockFriendContent from "./BlockFriendContent"
import { blockCancel, getBlockedList } from "api/blockApi";
import { IBlock } from "atoms/Block.type";

const BlockFriend = () => {
    const { data: blocks } = useQuery<IBlock[]>({
        queryKey: ['blocks'],
        queryFn: async () => await getBlockedList(),
      });

    return (
        <div className="mb-20">
            {blocks?.length ? (
            blocks.map((block, index) => (
                <BlockFriendContent key={index} userId={block.userId} campusName={block.campusName} campusSection={block.campusSection} name={block.nickname}/>
            ))):(
                <div className="flex justify-center">
                    차단된 친구가 없습니다.
                </div>
            )}
        </div>
    )
}

export default BlockFriend