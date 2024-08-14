import SendingIcon from 'icons/SendingIcon';
import CoinIcon from 'icons/CoinIcon';

import { Button } from 'components/ui/button';

import { useState } from 'react';
import { useForm } from 'react-hook-form';
import { useMutation } from '@tanstack/react-query';
import { postMessageSend } from 'api/messageApi';

import CoinUseModal from 'components/modals/CoinUseModal';
import InputModal from 'components/modals/InputModal';
import ResultCheckModal from 'components/modals/ResultCheckModal';

import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
  DialogFooter,
} from 'components/ui/dialog';

import { IPick } from 'atoms/Pick.type';
import { MESSAGE_COIN } from 'coins/coins';
import { useRecoilState } from 'recoil';
import { isMessageModalOpenState } from 'atoms/AlarmAtoms';

enum MessageModalStep {
  INPUT, // 쪽지 입력
  CONFIRM, // 쪽지 확인
  ALERT, // 쪽지 전송
  FAIL, // 쪽지 전송 실패
  POSTING, // 쪽지 전송 중
}

interface MessageForm {
  message: string;
}

interface MessageModalProps {
  pick: IPick;
  pickco: number;
  onMessageSent: (pickId: number) => void;
}

const MessageModal = ({ pick, pickco, onMessageSent }: MessageModalProps) => {
  const [step, setStep] = useState<MessageModalStep>(MessageModalStep.INPUT);
  const [open, setOpen] = useRecoilState<boolean>(isMessageModalOpenState);
  const [isModalVisible, setIsModalVisible] = useState<boolean>(true);
  const [message, setMessage] = useState<boolean>(pick.messageSend);

  // 쪽지 전송 mutation
  const mutation = useMutation({
    mutationKey: ['message', 'send'],
    mutationFn: postMessageSend,

    onMutate: () => {
      setStep(MessageModalStep.POSTING);
    },

    // 쪽지 전송 성공 시
    onSuccess: () => {
      setStep(MessageModalStep.ALERT);
      setTimeout(() => {
        setIsModalVisible(false);
        setMessage(true);
        onMessageSent(pick.id);
        setOpen(false); // 모달 닫기
      }, 1500);
    },
    onError: (error) => {
      setStep(MessageModalStep.FAIL);
    },
  });

  const {
    register,
    formState: { errors },
    handleSubmit,
    reset,
  } = useForm<MessageForm>();

  const onSubmit = (data: MessageForm) => {
    if (step === MessageModalStep.INPUT) {
      setStep(MessageModalStep.CONFIRM);
    } else if (step === MessageModalStep.CONFIRM) {
      mutation.mutate({
        pickId: pick.id,
        content: data.message,
      });
      reset();
    }
  };

  const onClose = () => {
    setOpen(false);
    setStep(MessageModalStep.INPUT);
  };

  const onInvalid = (errors: any) => {
    console.log('errors', errors);
  };

  return (
    <Dialog open={open} onOpenChange={(open) => !open && onClose()}>
      <DialogTrigger onClick={() => setOpen(true)}>
        {!message && <SendingIcon />}
      </DialogTrigger>
      {isModalVisible && (
        <DialogContent className="border rounded-lg bg-[#E9F2FD] mx-2 w-4/5">
          <DialogHeader>
            <DialogTitle className="flex flex-start text-color-5F86E9">
              쪽지 보내기
            </DialogTitle>
          </DialogHeader>
          {step === MessageModalStep.INPUT && (
            <div>
              <div className="my-3">{pick.question.content}</div>
              <InputModal
                name="message"
                register={register('message', {
                  required: '쪽지 내용을 입력해주세요.',
                  maxLength: {
                    value: 255,
                    message: '255글자 이하로 입력해주세요.',
                  },
                })}
                errors={errors}
              />
              <DialogFooter className="flex flex-row justify-end mt-3 relative">
                <Button
                  type="submit"
                  variant={pickco >= MESSAGE_COIN ? 'ssapick' : 'fault'}
                  size="messageButton"
                  className="flex flex-row items-center"
                  onClick={() => {
                    if (pickco >= MESSAGE_COIN) {
                      handleSubmit(onSubmit, onInvalid)();
                    }
                  }}
                >
                  <CoinIcon width={25} height={25} />
                  <h3 className="luckiest_guy ms-2 me-4 pt-1">
                    {MESSAGE_COIN}
                  </h3>
                  전송
                </Button>
                {pickco < MESSAGE_COIN && (
                  <span className="text-red-400 fixed bottom-2 right-[25px] text-[10px]">
                    <span className="luckiest_guy">PICKCO</span>가 부족합니다!
                  </span>
                )}
              </DialogFooter>
            </div>
          )}
          {step === MessageModalStep.CONFIRM && (
            <div>
              <CoinUseModal coin={MESSAGE_COIN} />
              <DialogFooter className="flex flex-row justify-end mt-3">
                <Button
                  type="submit"
                  variant="ssapick"
                  size="md"
                  onClick={() => {
                    handleSubmit(onSubmit, onInvalid)();
                  }}
                >
                  전송
                </Button>
              </DialogFooter>
            </div>
          )}
          {step === MessageModalStep.ALERT && (
            <ResultCheckModal content="전송이 완료되었습니다." />
          )}
          {step === MessageModalStep.FAIL && (
            <ResultCheckModal
              content="전송에 실패했습니다."
              detail="AI가 올바르지 않은 문장이라 판단했습니다."
            />
          )}
          {step === MessageModalStep.POSTING && (
            <ResultCheckModal
              content="전송 중입니다."
              detail="AI가 문장을 판단 중 입니다."
            />
          )}
        </DialogContent>
      )}
    </Dialog>
  );
};

export default MessageModal;
