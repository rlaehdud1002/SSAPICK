import { QueryClient, useMutation } from "@tanstack/react-query"
import { deleteFriend } from "api/friendApi"
import BlockModal from "components/modals/BlockModal"
import DeleteModal from "components/modals/DeleteModal"
import DeleteIcon from "icons/DeleteIcon"
import FriendBlockIcon from "icons/FriendBlockIcon"
import PointIcon from "icons/PointIcon"
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "../ui/popover"


const PopOver = () => {

  // const queryClient = new QueryClient();

  // // 친구 삭제 mutation
  // const mutation = useMutation({
  //   mutationKey: ['friends', 'delete'],
  //   mutationFn: deleteFriend,
  //   // 친구 삭제 후 친구 목록 새로 고침
  //   onSuccess: () => {
  //     queryClient.invalidateQueries({
  //       queryKey: ['friends']
  //     })
  //   }
  // })

  return (
    <Popover>
      <PopoverTrigger asChild>
        <div>
          <PointIcon />
        </div>
      </PopoverTrigger>
      <PopoverContent className="w-28 mr-5">
        <div className="flex flex-col items-center">
          <div className="flex items-center">
            <FriendBlockIcon width={22} height={22} />
            <BlockModal title="차단" />
          </div>
          <div className="flex items-center">
            <DeleteIcon width={22} height={22} />
            {/* <div onClick={mutation}> */}
            <DeleteModal title="삭제" />
            {/* </div> */}
          </div>
        </div>
      </PopoverContent>
    </Popover>
  )
}

export default PopOver