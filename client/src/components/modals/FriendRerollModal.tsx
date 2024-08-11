import { useMutation, useQuery } from '@tanstack/react-query';
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
import { patchPickUserReRoll } from 'api/pickApi';
import { IPickco } from 'atoms/User.type';
import { getPickco } from 'api/authApi';
import Loading from 'components/common/Loading';

interface FriendRerollModalProps {
  handleShuffle: () => void;
}

const FriendRerollModal = ({ handleShuffle }: FriendRerollModalProps) => {
  const [open, setOpen] = useState<boolean>(false);

  const { data: pickco, isLoading: isLoadingPickco } = useQuery<IPickco>({
    queryKey: ['pickco'],
    queryFn: getPickco,
  });

  const mutation = useMutation({
    mutationKey: ['reroll'],
    mutationFn: patchPickUserReRoll,
    onSuccess: () => {
      handleShuffle();
    },
    onError: () => {
      console.log('에러 ㅈㅈ');
    },
  });

  const onClick = () => {
    mutation.mutate();
    setOpen(false);
  };

  if (isLoadingPickco) {
    return <Loading />;
  }

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
        {pickco && (
          <DialogFooter className="flex flex-row justify-end relative">
            <Button
              type="submit"
              // variant="ssapick"
              variant={pickco.pickco >= USER_REROLL_COIN ? 'ssapick' : 'fault'}
              onClick={() => {
                if (pickco.pickco >= USER_REROLL_COIN) {
                  onClick();
                }
              }}
              size="messageButton"
            >
              <CoinIcon width={20} height={20} />
              <span className="luckiest_guy ml-1 mr-2 mt-1">
                {USER_REROLL_COIN}
              </span>
              <span>셔플</span>
            </Button>
            {pickco.pickco < USER_REROLL_COIN && (
              <span className="text-red-400 fixed bottom-2 right-[25px] text-[10px]">
                <span className="luckiest_guy">PICKCO</span>가 부족합니다!
              </span>
            )}
          </DialogFooter>
        )}
      </DialogContent>
    </Dialog>
  );
};

export default FriendRerollModal;
