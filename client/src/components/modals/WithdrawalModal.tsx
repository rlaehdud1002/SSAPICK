import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from 'components/ui/dialog';

import { Button } from 'components/ui/button';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import ResultCheckModal from './ResultCheckModal';
import { useMutation } from '@tanstack/react-query';
import { withdrawal } from 'api/authApi';

enum WithdrawalModalStep {
  CONFIRM,
  ALERT,
}
interface HintModalProps {
  title: string;
}

const WithdrawalModal = ({ title }: HintModalProps) => {
  const [open, setOpen] = useState<boolean>(false);
  const [step, setStep] = useState<WithdrawalModalStep>(
    WithdrawalModalStep.CONFIRM,
  );
  const [isModalVisible, setIsModalVisible] = useState<boolean>(true);

  const naivgate = useNavigate();
  const navigateToHome = () => {
    naivgate('/');
  };

  useEffect(() => {
    if (step === WithdrawalModalStep.ALERT) {
      const timer = setTimeout(() => {
        setIsModalVisible(false);
        setOpen(false);
        navigateToHome();
      }, 1000);
      return () => clearTimeout(timer);
    }
  });

  const navigate = useNavigate();

  const mutation = useMutation({
    mutationFn: withdrawal,
    onSuccess: () => {
      console.log("회원탈퇴 성공");
      navigate("/");
    }
  });

  const onSubmit = () => {
    setStep(WithdrawalModalStep.ALERT);
    mutation.mutate();
  };

  const onClose = () => {
    setOpen(false);
    setStep(WithdrawalModalStep.CONFIRM);
  };

  return (
    <Dialog open={open} onOpenChange={(open) => !open && onClose()}>
      <DialogTrigger className="text-sm ml-2" onClick={() => setOpen(true)}>
        {title}
      </DialogTrigger>
      {isModalVisible && (
        <DialogContent className="border rounded-lg bg-[#E9F2FD] mx-2 w-4/5 relative">
          <DialogHeader>
            <DialogTitle className="flex flex-start text-color-5F86E9">
              {title}
            </DialogTitle>
          </DialogHeader>
          {step === WithdrawalModalStep.CONFIRM && (
            <div>
              <DialogDescription className="flex justify-center">
                <h3 className="flex flex-row my-10 items-center">
                  회원 탈퇴를 하시겠습니까?
                </h3>
              </DialogDescription>
              <DialogFooter className="flex flex-row justify-end">
                
                <Button variant="ssapick" size="md" onClick={onSubmit}>
                  확인
                </Button>
              </DialogFooter>
            </div>
          )}
          {step === WithdrawalModalStep.ALERT && (
            <ResultCheckModal content="회원탈퇴가 완료되었습니다." />
          )}
        </DialogContent>
      )}
    </Dialog>
  );
};

export default WithdrawalModal;
