import ResultCheckModal from 'components/modals/ResultCheckModal';
import WarningIcon from 'icons/WarningIcon';

import { Button } from 'components/ui/button';

import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
  DialogFooter,
} from 'components/ui/dialog';

import { useEffect, useState } from 'react';
import { IPickCreate } from 'atoms/Pick.type';
import PassIcon from 'icons/PassIcon';

enum WarningStep {
  CHECK,
  ALERT,
}

interface WarningModalProps {
  title: string;
  question: any;
  blockPassCount: number;
  userPick: (data: IPickCreate) => void;
}

const WarningModal = ({
  question,
  title,
  blockPassCount,
  userPick,
}: WarningModalProps) => {
  const [step, setStep] = useState<WarningStep>(WarningStep.CHECK);
  const [open, setOpen] = useState<boolean>(false);
  const [isModalVisible, setIsModalVisible] = useState<boolean>(true);

  const handlePick = () => {
    userPick({
      questionId: question.id,
      status: title === 'block' ? 'BLOCK' : 'PASS',
    });

    setStep(WarningStep.ALERT);
  };

  // 마지막 모달이 실행된 후 1초 뒤 자동으로 닫힘
  useEffect(() => {
    if (step === WarningStep.ALERT) {
      const timer = setTimeout(() => {
        setIsModalVisible(false);
      }, 1000);

      return () => clearTimeout(timer);
    }
  }, [step]);

  const onClose = () => {
    setOpen(false);
    setStep(WarningStep.CHECK);
  };

  return (
    <Dialog open={open} onOpenChange={(open) => !open && onClose()}>
      <DialogTrigger onClick={() => setOpen(true)}>
        {title === 'block' ? (
          <WarningIcon width={20} height={20} className="mx-1" circle />
        ) : (
          <PassIcon />
        )}
      </DialogTrigger>
      {isModalVisible && (
        <DialogContent className="border rounded-lg bg-[#E9F2FD] mx-2 w-4/5">
          <DialogHeader>
            <DialogTitle className="flex flex-start text-color-5F86E9">
              {title === 'block' ? '질문 신고' : '질문 패스'}
            </DialogTitle>
          </DialogHeader>
          {step === WarningStep.CHECK && (
            <div>
              <div className="flex flex-col items-center my-12 text-center">
                <p>
                  이 질문을 {title === 'block' ? '신고' : '패스'}하시겠습니까?
                </p>
                <p className="bg-[#92AEF4]/30 rounded-lg text-[#4D5BDC] w-4/5 p-1 mt-3">
                  {question.content}
                </p>
                <p>{blockPassCount} / 5</p>
              </div>
              <DialogFooter className="flex flex-row justify-center">
                <Button
                  type="submit"
                  className={`bg-[${title === 'block' ? '#8C8C8C' : '#E95F5F'}] `}
                  onClick={handlePick}
                >
                  취소
                </Button>
                <Button
                  type="submit"
                  className={`bg-[${title === 'block' ? '#E95F5F' : '#5F86E9'}] `}
                  onClick={handlePick}
                >
                  {title === 'block' ? '신고' : '패스'}
                </Button>
              </DialogFooter>
            </div>
          )}
          {step === WarningStep.ALERT && (
            <ResultCheckModal
              content={
                title === 'block'
                  ? '질문 신고가 완료되었습니다'
                  : '질문 패스가 완료되었습니다'
              }
            />
          )}
        </DialogContent>
      )}
    </Dialog>
  );
};

export default WarningModal;
