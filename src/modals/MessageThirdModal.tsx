import SendingIcon from 'icons/SendingIcon';
import CoinIcon from 'icons/CoinIcon';

import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
  DialogFooter,
} from 'components/ui/dialog';

const MessageThirdModal = () => {

  return (
    <Dialog>
      <DialogTrigger>
        <SendingIcon />
      </DialogTrigger>
      <DialogContent className="border rounded-md bg-[#E9F2FD] mx-2">
        <DialogHeader>
          <DialogTitle className="flex flex-start">쪽지 보내기</DialogTitle>
          <DialogDescription className="flex justify-center my-10">
            <h1>세 번째 모달</h1>
          </DialogDescription>
        </DialogHeader>
        <DialogFooter className="text-right">Button 자리</DialogFooter>
      </DialogContent>
    </Dialog>
  );
};

export default MessageThirdModal;
