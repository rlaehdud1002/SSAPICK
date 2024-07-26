import {
  Dialog,
  DialogTrigger,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogFooter,
} from 'components/ui/dialog';

import CoinIcon from 'icons/CoinIcon';

import { useEffect, useState } from 'react';

const LocationModal = () => {
  const [open, setOpen] = useState<boolean>(false);

  useEffect(() => {
    const timer = setTimeout(() => {
      setOpen(false);
    }, 1000);

    return () => clearTimeout(timer);
  }, [open]);

  return (
    <Dialog open={open} onOpenChange={(open) => setOpen(open)}>
      <DialogTrigger onClick={() => setOpen(true)}>modalButton</DialogTrigger>
      {open && (
        <DialogContent className="border rounded-lg bg-[#E9F2FD] mx-2 w-4/5">
          <DialogHeader>
            <DialogTitle className="flex flex-start">코인 획득</DialogTitle>
            <DialogDescription className="flex justify-center my-10 items-center text-color-000855">
              <h3 className="flex flex-row my-10">
                <CoinIcon width={25} height={25} />
                <h3 className="luckiest_guy ms-1 me-2 pt-1">1</h3>을
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
