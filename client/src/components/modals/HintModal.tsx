import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
  DialogFooter,
} from 'components/ui/dialog';

import { Button } from 'components/ui/button';
import CoinUseModal from 'components/modals/CoinUseModal';

import { useState } from 'react';
import { useMutation } from '@tanstack/react-query';
import { getHint } from 'api/pickApi';
import { HINT_OPEN_COIN } from 'coins/coins';

interface HintModalProps {
  title: string;
  pickId: number;
  pickco: number;
}

const HintModal = ({ title, pickId, pickco }: HintModalProps) => {
  const [open, setOpen] = useState<boolean>(false);
  const [hint, setHint] = useState<string>(title);

  // 힌트 조회 mutation
  const mutation = useMutation({
    mutationKey: ['hint', 'get'],
    mutationFn: getHint,

    // 힌트 조회 성공 시
    onSuccess: (data) => {
      console.log('hint', data);
      setHint(data);
    },
  });

  return (
    <Dialog
      open={open}
      onOpenChange={(open) => !open && setOpen((prev) => !prev)}
    >
      <DialogTrigger
        onClick={() => {
          if (hint === '?') {
            setOpen(true);
          }
        }}
      >
        <div className="my-1 text-xs text-[#5f86e9]">{hint.split(':')[0]}</div>
        <div className="text-base text-[#000855]">{hint.split(':')[1]}</div>
      </DialogTrigger>
      <DialogContent className="border rounded-lg bg-[#E9F2FD] mx-2 w-4/5">
        <DialogHeader>
          <DialogTitle className="flex flex-start text-color-5F86E9">
            힌트 공개
          </DialogTitle>
          <CoinUseModal coin={HINT_OPEN_COIN} />
        </DialogHeader>
        <DialogFooter className="flex flex-row justify-end relative">
          <Button
            variant={pickco >= HINT_OPEN_COIN ? 'ssapick' : 'fault'}
            size="md"
            onClick={() => {
              mutation.mutate(pickId);
              setOpen(false);
            }}
          >
            확인
          </Button>
          {pickco < HINT_OPEN_COIN && (
            <span className="text-red-400 fixed bottom-1.5 right-[14px] text-[10px]">
              <span className="luckiest_guy">PICKCO</span>가 부족합니다!
            </span>
          )}
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
};

export default HintModal;
