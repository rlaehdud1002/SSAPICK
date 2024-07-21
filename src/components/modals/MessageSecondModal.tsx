import CoinIcon from 'icons/CoinIcon';
import { Button } from 'components/ui/button';

import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogFooter,
} from 'components/ui/dialog';

interface MessageSecondModalProps {
  show: boolean;
  onClose: () => void;
  onCreate: () => void;
}

const MessageSecondModal = ({
  show,
  onClose,
  onCreate,
}: MessageSecondModalProps) => {
  return (
    <Dialog open={show} onOpenChange={(open) => (open ? undefined : onClose())}>
      <DialogContent className="border rounded-md bg-[#E9F2FD] mx-2 w-4/5">
        <DialogHeader>
          <DialogTitle className="flex flex-start">쪽지 보내기</DialogTitle>
          <DialogDescription className="flex justify-center my-10">
            <h3 className="flex flex-row">
              <CoinIcon width={25} height={25} />
              <h3 className="luckiest_guy">1</h3>이 사용됩니다.
            </h3>
          </DialogDescription>
        </DialogHeader>
        <DialogFooter className="text-right">
          <Button
            variant="ssapick"
            size="lg"
            onClick={() => {
              onCreate();
              onClose();
            }}
          >
            <CoinIcon width={25} height={25} />
            <h3 className="luckiest_guy ms-2 me-4">1</h3>전송
          </Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
};

export default MessageSecondModal;
