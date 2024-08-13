import { useMutation, useQueryClient } from "@tanstack/react-query";
import { selectFriends } from "api/locationApi";
import {
  Dialog,
  DialogTrigger,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogFooter,
} from "components/ui/dialog";

import CoinIcon from "icons/CoinIcon";

import { useState } from "react";

interface LocationModalProps {
  profileImage: string;
  username: string;
}

const LocationModal = ({ profileImage, username }: LocationModalProps) => {
  const queryClient = useQueryClient();
  const mutation = useMutation({
    mutationKey: ["select"],
    mutationFn: selectFriends,
    onSuccess: () => {
      console.log("내 주변 유저 클릭 성공");
      setTimeout(() => {
        queryClient.invalidateQueries({
          queryKey: ["location"],
        });
        setOpen(false);
      }, 1000);
    },
  });
  const [open, setOpen] = useState<boolean>(false);

  return (
    <Dialog open={open} onOpenChange={(open) => setOpen(open)}>
      <DialogTrigger
        onClick={() => {
          mutation.mutate({ username });
          setOpen(true);
        }}
      >
        <img
          className="w-12 h-12 rounded-full"
          src={profileImage}
          alt="profileImage"
        />
      </DialogTrigger>
      {open && (
        <DialogContent className="border rounded-lg bg-[#E9F2FD] mx-2 w-4/5">
          <DialogHeader>
            <DialogTitle className="flex flex-start">코인 획득</DialogTitle>
            <DialogDescription className="flex justify-center my-10 items-center text-color-000855">
              <h3 className="flex flex-row my-10">
                <CoinIcon width={25} height={25} />
                <h3 className="luckiest_guy ml-1 mr-2 pt-1">1</h3>을
                획득하셨습니다!
              </h3>
            </DialogDescription>
          </DialogHeader>
          <DialogFooter className="flex flex-row justify-end"></DialogFooter>
        </DialogContent>
      )}
    </Dialog>
  );
};

export default LocationModal;
