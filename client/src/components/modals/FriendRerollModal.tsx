import { useMutation } from '@tanstack/react-query';
import { USER_REROLL_COIN } from 'coins/coins';
import { Button } from 'components/ui/button';
import {
  Dialog,
  DialogHeader,
  DialogContent,
  DialogTitle,
  DialogTrigger,
  DialogFooter,
} from 'components/ui/dialog';
import CoinIcon from 'icons/CoinIcon';
import ShuffleIcon from 'icons/ShuffleIcon';
import { useState } from 'react';
import { patchPickUserReRoll } from 'api/pickApi'

interface FriendRerollModalProps {
  handleShuffle: () => void;
}

const FriendRerollModal = ({ handleShuffle }: FriendRerollModalProps) => {
  const [open, setOpen] = useState<boolean>(false);


  const mutation = useMutation({
    mutationKey: ['reroll'],
    mutationFn: patchPickUserReRoll,
    onSuccess: () => {
      console.log('차단 해제 성공');
    },
    onError: ()=>{
      console.log('에러 ㅈㅈ')
    }
  });

  const onClick = () => {
    mutation.mutate();

    handleShuffle();
    setOpen(false);
  };

  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogTrigger>
        <ShuffleIcon className="cursor-pointer" />
      </DialogTrigger>
      <DialogContent className="border rounded-lg bg-[#E9F2FD] mx-2 w-4/5">
        <DialogHeader>
          <DialogTitle className="flex flex-start text-color-5F86E9">
            친구 셔플
          </DialogTitle>
          <div>
            <div className="flex flex-col items-center my-16 text-center">
              <p>친구를 셔플하시겠습니까?</p>
            </div>
          </div>
        </DialogHeader>
        <DialogFooter className="flex flex-row justify-end">
          <Button
            type="submit"
            variant="ssapick"
            onClick={onClick}
            size="messageButton"
          >
            <CoinIcon width={20} height={20} />
            <span className="luckiest_guy ml-1 mr-2 mt-1">{USER_REROLL_COIN}</span>
            <span>셔플</span>
          </Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
};

export default FriendRerollModal;
