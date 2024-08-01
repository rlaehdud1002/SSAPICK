import ResultCheckModal from 'components/modals/ResultCheckModal';
import { Button } from 'components/ui/button';
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
  DialogFooter,
} from 'components/ui/dialog';

import DeleteIcon from 'icons/DeleteIcon';
import WarningIcon from 'icons/WarningIcon';
import { useEffect, useState } from 'react';

enum WarningDeleteStep {
  CHECK,
  ALERT,
}

interface WarningDeleteModalProps {
  title: string;
  message?: string;
}

const WarningDeleteModal = ({ title, message }: WarningDeleteModalProps) => {
  const [step, setStep] = useState<WarningDeleteStep>(WarningDeleteStep.CHECK);
  const [open, setOpen] = useState<boolean>(false);
  const [isModalVisible, setIsModalVisible] = useState<boolean>(true);

  // 마지막 모달이 실행된 후 1초 뒤 자동으로 닫힘
  useEffect(() => {
    if (step === WarningDeleteStep.ALERT) {
      const timer = setTimeout(() => {
        setIsModalVisible(false);
      }, 1000);

      return () => clearTimeout(timer);
    }
  }, [step]);

  const Click = () => {
    setStep(WarningDeleteStep.ALERT);
  };

  const onClose = () => {
    setOpen(false);
    setStep(WarningDeleteStep.CHECK);
  };
  return (
    <Dialog open={open} onOpenChange={(open) => !open && onClose()}>
      <DialogTrigger onClick={() => setOpen(true)}>
        <div className="flex flex-row">
          {title === '신고' ? (
            <WarningIcon width={24} height={24} className="mr-3" />
          ) : (
            <DeleteIcon width={24} height={24} className="mr-3" />
          )}
          <span>{title}</span>
        </div>
      </DialogTrigger>
      {isModalVisible && (
        <DialogContent className="border rounded-xl bg-[#E9F2FD] mx-2 w-4/5">
          <DialogHeader>
            <DialogTitle className="flex flex-start text-color-5F86E9">
              쪽지 {title}
            </DialogTitle>
          </DialogHeader>
          {step === WarningDeleteStep.CHECK && (
            <div>
              <div className="flex flex-col items-center my-16 text-center">
                <p>이 쪽지를 {title}하시겠습니까?</p>
                {title === '신고' && (
                  <p className="bg-[#92AEF4]/30 rounded-lg text-[#4D5BDC] w-4/5 p-1 mt-3">
                    {message}
                  </p>
                )}
              </div>
              <DialogFooter className="flex flex-row justify-end">
                <Button
                  type="submit"
                  className="bg-[#E95F5F] hover:bg-red-400 "
                  onClick={Click}
                >
                  {title}
                </Button>
              </DialogFooter>
            </div>
          )}
          {step === WarningDeleteStep.ALERT && (
            <ResultCheckModal content={`쪽지 ${title}가 완료되었습니다.`} />
          )}
        </DialogContent>
      )}
    </Dialog>
  );
};

export default WarningDeleteModal;
