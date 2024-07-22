import SendingIcon from 'icons/SendingIcon';
import CoinIcon from 'icons/CoinIcon';

import { Button } from 'components/ui/button';

import { useState } from 'react';
import { useForm } from 'react-hook-form';

import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
  DialogFooter,
} from 'components/ui/dialog';

import MessageInputModal from 'components/modals/MessageInputModal';
import CoinUseModal from 'components/modals/CoinUseModal';
import MessageCheckModal from 'components/modals/MessageCheckModal';

enum MessageModalStep {
  INPUT, // 쪽지 입력
  CONFIRM, // 쪽지 확인
  ALERT, // 쪽지 전송
}

interface MessageForm {
  message: string;
}

const MessageFirstModal = () => {
  const [step, setStep] = useState<MessageModalStep>(MessageModalStep.INPUT);
  const [open, setOpen] = useState<boolean>(false);

  const {
    register,
    formState: { errors },
    handleSubmit,
    reset,
  } = useForm<MessageForm>();

  const onSubmit1 = (data: MessageForm) => {
    console.log('ok', data);
    setStep(MessageModalStep.CONFIRM);
    reset();
  };

  const onSubmit2 = () => {
    console.log('ok');
    setStep(MessageModalStep.ALERT);
  };

  const onClose = () => {
    setOpen(false);
    setStep(MessageModalStep.INPUT);
  };

  const onInvalid1 = (errors: any) => {
    console.log('errors', errors);
  };

  const onInvalid2 = (errors: any) => {
    console.log('errors', errors);
  };

  return (
    <Dialog open={open} onOpenChange={(open) => !open && onClose()}>
      <DialogTrigger onClick={() => setOpen(true)}>
        <SendingIcon />
      </DialogTrigger>
      <DialogContent className="border rounded-md bg-[#E9F2FD] mx-2 w-4/5 relative">
        <DialogHeader>
          <DialogTitle className="flex flex-start">쪽지 보내기</DialogTitle>
        </DialogHeader>
        {step === MessageModalStep.INPUT && (
          <div>
            <MessageInputModal
              register={register('message', {
                required: '쪽지 내용을 입력해주세요.',
                maxLength: {
                  value: 100,
                  message: '100글자 이하로 입력해주세요.',
                },
              })}
              errors={errors}
            />
            <DialogFooter className="flex flex-row justify-end mt-3">
              <Button
                type="submit"
                variant="ssapick"
                size="messageButton"
                onClick={() => {
                  handleSubmit(onSubmit1, onInvalid1)();
                }}
              >
                <CoinIcon width={25} height={25} />
                <h3 className="luckiest_guy ms-2 me-4">1</h3>전송
              </Button>
            </DialogFooter>
          </div>
        )}
        {step === MessageModalStep.CONFIRM && (
          <div>
            <CoinUseModal />
            <DialogFooter className="flex flex-row justify-end mt-3">
              <Button
                type="submit"
                variant="ssapick"
                size="md"
                onClick={() => {
                  handleSubmit(onSubmit2, onInvalid2)();
                }}
              >
                전송
              </Button>
            </DialogFooter>
          </div>
        )}
        {step === MessageModalStep.ALERT && <MessageCheckModal />}
      </DialogContent>
    </Dialog>
  );
};

export default MessageFirstModal;
