import SendingIcon from 'icons/SendingIcon';
import CoinIcon from 'icons/CoinIcon';
import UserIcon from 'icons/UserIcon';
import { Button } from 'components/ui/button';
import { Input } from 'components/ui/input';

import { useForm } from 'react-hook-form';
import { ErrorMessage } from '@hookform/error-message';

import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
  DialogFooter,
} from 'components/ui/dialog';

interface MessageFirstModalProps {
  show: boolean;
  onOpen: () => void;
  onClose: () => void;
  onCreate: () => void;
}

interface MessageForm {
  message: string;
}

const MessageFirstModal = ({
  show,
  onOpen,
  onClose,
  onCreate,
}: MessageFirstModalProps) => {
  const {
    register,
    formState: { errors },
    handleSubmit,
  } = useForm<MessageForm>();

  const onSubmit = (data: MessageForm) => {
    console.log('ok', data);
    onCreate();
    onClose();
  };

  const onInvalid = (errors: any) => {
    console.log('errors', errors);
  };

  return (
    <Dialog open={show} onOpenChange={(isOpen) => !isOpen && onClose()}>
      <DialogTrigger onClick={onOpen}>
        <SendingIcon />
      </DialogTrigger>
      <DialogContent className="border rounded-md bg-[#E9F2FD] mx-2 w-4/5 relative">
        <DialogHeader>
          <DialogTitle className="flex flex-start">쪽지 보내기</DialogTitle>
          <DialogDescription className="flex flex-col justify-center">
            <div className="my-3 flex flex-row">
              <UserIcon />
              <h3 className="ms-3">11기 2반</h3>
            </div>
            <Input
              type="text"
              className="input-box border-none h-20 focus:outline-none"
              register={register('message', {
                required: '쪽지 내용을 입력해주세요.',
                maxLength: {
                  value: 100,
                  message: '100글자 이하로 입력해주세요.',
                },
              })}
            />
            <ErrorMessage
              errors={errors}
              name="message"
              render={({ message }) => (
                <h6 className="text-red-400 text-left text-xs my-1 fixed bottom-[50px]">{message}</h6>
              )}
            />
          </DialogDescription>
        </DialogHeader>
        <DialogFooter className="flex flex-row justify-end mt-3">
          <Button
            type="submit"
            variant="ssapick"
            size="messageButton"
            onClick={handleSubmit(onSubmit, onInvalid)}
          >
            <CoinIcon width={25} height={25} />
            <h3 className="luckiest_guy ms-2 me-4">1</h3>전송
          </Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
};

export default MessageFirstModal;
