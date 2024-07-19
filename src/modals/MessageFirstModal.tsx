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

const MessageFirstModal = () => {

  return (
    <Dialog>
      <DialogTrigger>
        <SendingIcon />
      </DialogTrigger>
      <DialogContent className="border rounded-md bg-[#E9F2FD] mx-2">
        <DialogHeader>
          <DialogTitle className="flex flex-start">쪽지 보내기</DialogTitle>
          <DialogDescription className="flex justify-center my-10">
            <h3 className="flex flex-row">
              <CoinIcon width={25} height={25} />
              <h3 className="luckiest_guy">1</h3>이 사용됩니다.
            </h3>
          </DialogDescription>
        </DialogHeader>
        <DialogFooter className="text-right">Button 자리</DialogFooter>
      </DialogContent>
    </Dialog>
  );
};

export default MessageFirstModal;
