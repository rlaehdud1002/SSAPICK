import { Button } from 'components/ui/button';

import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogFooter,
} from 'components/ui/dialog';

interface CheckModalProps {
  title: string;
  innerText: string;
  show: boolean;
  onClose: () => void;
}

const CheckModal = ({ title, innerText, show, onClose }: CheckModalProps) => {
  return (
    <Dialog open={show} onOpenChange={(open) => (open ? undefined : onClose())}>
      <DialogContent className="border rounded-md bg-[#E9F2FD] mx-2 w-4/5">
        <DialogHeader>
          <DialogTitle className="flex flex-start">{title}</DialogTitle>
          <DialogDescription className="flex justify-center">
            <h1 className="my-12">{innerText}</h1>
          </DialogDescription>
        </DialogHeader>
        <DialogFooter className="flex flex-row justify-end">
          <Button variant="ssapick" onClick={onClose}>
            닫기
          </Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
};

export default CheckModal;
