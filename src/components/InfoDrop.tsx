import { Button } from "components/ui/button";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuRadioGroup,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "components/ui/dropdown-menu";
import * as React from "react";

import DropContent from "./DropContent";

interface InfoDropProps {
  title: string,
}

const InfoDrop = ({ title }: InfoDropProps) => {
  const [position, setPosition] = React.useState("bottom")
  return (
    <DropdownMenu>
      <DropdownMenuTrigger asChild>
        <Button>{title}</Button>
      </DropdownMenuTrigger>
      <DropdownMenuContent className="w-56">
        <DropdownMenuSeparator />
        <DropdownMenuRadioGroup value={position} onValueChange={setPosition}>
          <DropContent content="남자" />
          <DropContent content="여자" />
        </DropdownMenuRadioGroup>
      </DropdownMenuContent>
    </DropdownMenu>

  )
}

export default InfoDrop;