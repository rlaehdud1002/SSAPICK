import { Button } from 'components/ui/button';
import WarningIcon from 'icons/WarningIcon';
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
  DialogFooter,
} from 'components/ui/dialog';

import { useEffect, useState } from 'react';
import { DialogDescription } from '@radix-ui/react-dialog';

enum WarningStep {
  CHECK,
  ALERT,
}

const WarningModal = () => {
  const [step, setStep] = useState<WarningStep>(WarningStep.CHECK);
  const [open, setOpen] = useState<boolean>(false);
  const [isModalVisible, setIsModalVisible] = useState<boolean>(true);

  // 마지막 모달이 실행된 후 1초 뒤 자동으로 닫힘
  useEffect(() => {
    if (step === WarningStep.ALERT) {
      const timer = setTimeout(() => {
        setIsModalVisible(false);
      }, 1000);

      return () => clearTimeout(timer);
    }
  }, [step]);

  const Click = () => {
    setStep(WarningStep.ALERT);
  };

  const onClose = () => {
    setOpen(false);
    setStep(WarningStep.CHECK);
  };

  return (
    <Dialog open={open} onOpenChange={(open) => !open && onClose()}>
      <DialogTrigger onClick={() => setOpen(true)}>
        <WarningIcon width={20} height={20} className='mx-1'/>
      </DialogTrigger>
      {isModalVisible && (
        <DialogContent className="border rounded-lg bg-[#E9F2FD] mx-2 w-4/5">
          <DialogHeader>
            <DialogTitle className="flex flex-start text-color-5F86E9">
              질문 만들기
            </DialogTitle>
          </DialogHeader>
          {step === WarningStep.CHECK && (
            <div>
              <DialogDescription>
                <div className="flex flex-col items-center my-16 text-center">
                  <p>이 질문을 신고하시겠습니까?</p>
                  <p className="bg-[#92AEF4]/30 rounded-lg text-[#4D5BDC] w-4/5 p-1 mt-3">
                    프로젝트 같이 하고 싶은 사람?
                  </p>
                </div>
              </DialogDescription>
              <DialogFooter className="flex flex-row justify-end">
                <Button
                  type="submit"
                  className="bg-[#E95F5F] hover:bg-red-400 "
                  onClick={Click}
                >
                  신고
                </Button>
              </DialogFooter>
            </div>
          )}
          {step === WarningStep.ALERT && (
            <div>
              <DialogDescription>
                <div className="text-center my-16">
                  질문 신고가 완료되었습니다.
                </div>
              </DialogDescription>
            </div>
          )}
        </DialogContent>
      )}
    </Dialog>
  );
};

export default WarningModal;
