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

interface HintModalProps {
  title: string;
}

const HintModal = ({ title }: HintModalProps) => {
  const [open, setOpen] = useState<boolean>(false);
  const [hint, setHint] = useState<string>(title);

  // 힌트 조회 mutation
  const mutation = useMutation({
    mutationKey: ['hint', 'get'],
    mutationFn: getHint,

    // 힌트 조회 성공 시
    onSuccess: (data) => {
      setHint(data);
    },
  });

  return (
    <Dialog
      open={open}
      onOpenChange={(open) => !open && setOpen((prev) => !prev)}
    >
      <DialogTrigger
        className="luckiest_guy text-color-5F86E9"
        onClick={() => {
          if (hint === '?') {
            setOpen(true);
          }
        }}
      >
        {hint}
      </DialogTrigger>
      <DialogContent className="border rounded-lg bg-[#E9F2FD] mx-2 w-4/5">
        <DialogHeader>
          <DialogTitle className="flex flex-start text-color-5F86E9">
            힌트 공개
          </DialogTitle>
          <CoinUseModal coin={1} />
        </DialogHeader>
        <DialogFooter className="flex flex-row justify-end">
          <Button
            variant="ssapick"
            size="md"
            onClick={() => {
              mutation.mutate();
              setOpen(false);
            }}
          >
            확인
          </Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
};

export default HintModal;
