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

enum DeletelStep {
  CONFIRM,
  ALERT
}

interface DeleteModalProps {
  title: string;

}

const DeleteModal = ({ title }: DeleteModalProps) => {
  const [open, setOpen] = useState<boolean>(false);
  const [step, setStep] = useState<DeletelStep>(DeletelStep.CONFIRM);
  const [isModalVisible, setIsModalVisible] = useState<boolean>(true);

  const naivgate = useNavigate();
  const navigateToFriendList = () => {
    naivgate('/friendlist');
    
  }


  useEffect(() => {
    if (step === DeletelStep.ALERT) {
      const timer = setTimeout(() => {
        setIsModalVisible(false);
        navigateToFriendList()
        setOpen(false);
      }, 1000);
      return () => clearTimeout(timer);
    }
  });


  const onSubmit = () => {
    setStep(DeletelStep.ALERT);
  }


  const onClose = () => {
    setOpen(false);
    setStep(DeletelStep.CONFIRM);
  }

  return (
    <Dialog open={open} onOpenChange={(open) => !open && onClose()}
    >
      <DialogTrigger
        className="ml-2"
        onClick={() => setOpen(true)}
      >
        {title}
      </DialogTrigger>
      {isModalVisible && (<DialogContent className="border rounded-lg bg-[#E9F2FD] mx-2 w-4/5 relative">
        <DialogHeader>
          <DialogTitle className="flex flex-start text-color-5F86E9">
            {title}
          </DialogTitle>
        </DialogHeader>
        {step === DeletelStep.CONFIRM && (
          <div>
            <DialogDescription className="flex justify-center">
              <h3 className="flex flex-row my-10 items-center">삭제하시겠습니까?</h3>
            </DialogDescription>
            <DialogFooter className="flex flex-row justify-end">
              <Button variant="ssapick" size="md" onClick={onSubmit}>
                확인
              </Button>
            </DialogFooter>
          </div>
        )}
        {step === DeletelStep.ALERT && <ResultCheckModal content="삭제가 완료되었습니다." />}
      </DialogContent>)}
    </Dialog>
  );
}

export default DeleteModal;