import PointIcon from "icons/PointIcon"
import { Button } from "../ui/button"

import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "../ui/popover"

const PopOver = () => {
  return (
    <Popover>
      <PopoverTrigger asChild>
        {/* <Button variant="outline">Open popover</Button> */}
        <div>
        <PointIcon/>
        </div>
      </PopoverTrigger>
      <PopoverContent className="w-32 mr-5">
        <div className="flex flex-col">
        <span className="my-1">차단</span>
        <span className="my-1">삭제</span>
        </div>
      </PopoverContent>
    </Popover>
  )
}

export default PopOver