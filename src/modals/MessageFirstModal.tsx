import SendingIcon from 'icons/SendingIcon';
import CoinIcon from 'icons/CoinIcon';
import UserIcon from 'icons/UserIcon';
import { Button } from 'components/ui/button';
import { Input } from 'components/ui/input';

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
      <DialogContent className="border rounded-md bg-[#E9F2FD] mx-2 w-4/5">
        <DialogHeader>
          <DialogTitle className="flex flex-start">쪽지 보내기</DialogTitle>
          <DialogDescription className="flex flex-col justify-center">
            <div className='my-3 flex flex-row'>
              <UserIcon />
              <h3 className='ms-3'>11기 2반</h3>
            </div>
            <Input
              className="input-box border-none h-10 focus:outline-none"
              type="text"
            />
          </DialogDescription>
        </DialogHeader>
        <DialogFooter className="text-right">
          <Button className="bg-ssapick" size="lg">
            <CoinIcon width={25} height={25} />
            <h3 className="luckiest_guy ms-2 me-4">1</h3>전송
          </Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
};

export default MessageFirstModal;
