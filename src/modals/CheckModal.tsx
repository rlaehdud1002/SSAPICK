import SendingIcon from 'icons/SendingIcon';
import PlusIcon from 'icons/PlusIcon';

import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from 'components/ui/dialog';

interface CheckModalProps {
  title: string;
  innerText: string;
}

const CheckModal = ({ title, innerText }: CheckModalProps) => {
  return (
    <Dialog>
      <DialogTrigger>
        {title === '쪽지 보내기' ? <SendingIcon /> : <PlusIcon />}
      </DialogTrigger>
      <DialogContent className="border rounded-md bg-[#E9F2FD] mx-2 w-4/5">
        <DialogHeader>
          <DialogTitle className="flex flex-start">{title}</DialogTitle>
          <DialogDescription className="flex justify-center">
            <h1 className="my-12">{innerText}</h1>
          </DialogDescription>
        </DialogHeader>
      </DialogContent>
    </Dialog>
  );
};

export default CheckModal;