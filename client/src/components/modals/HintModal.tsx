import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
  DialogFooter,
} from 'components/ui/dialog';

import { Button } from 'components/ui/button';

import CoinIcon from 'icons/CoinIcon';

import { useState } from 'react';
import CoinUseModal from 'components/modals/CoinUseModal';

interface HintModalProps {
  title: string;
}

const HintModal = ({ title }: HintModalProps) => {
  const [open, setOpen] = useState<boolean>(false);

  return (
    <Dialog
      open={open}
      onOpenChange={(open) => !open && setOpen((prev) => !prev)}
    >
      <DialogTrigger
        className="luckiest_guy text-color-5F86E9"
        onClick={() => setOpen(true)}
      >
        {title}
      </DialogTrigger>
      <DialogContent className="border rounded-lg bg-[#E9F2FD] mx-2 w-4/5">
        <DialogHeader>
          <DialogTitle className="flex flex-start text-color-5F86E9">
            힌트 공개
          </DialogTitle>
          <CoinUseModal coin={1} />
        </DialogHeader>
        <DialogFooter className="flex flex-row justify-end">
          <Button variant="ssapick" size="md" onClick={() => setOpen(false)}>
            확인
          </Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
};

export default HintModal;
