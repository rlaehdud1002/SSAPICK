import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from 'components/ui/dialog';

import { useMutation, useQueryClient } from '@tanstack/react-query';
import { deleteFriend } from 'api/friendApi';
import { Button } from 'components/ui/button';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import ResultCheckModal from './ResultCheckModal';

enum DeletelStep {
  CONFIRM,
  ALERT,
}

interface DeleteModalProps {
  title: string;
  userId: number;
}

const DeleteModal = ({ title, userId }: DeleteModalProps) => {
  const queryClient = useQueryClient();
  // 친구 삭제 mutation
  const mutation = useMutation({
    mutationKey: ['friends'],
    mutationFn: deleteFriend,
    // 친구 삭제 후 친구 목록 새로 고침
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ['friends'],
      });
    },
    onError: (error) => {},
  });

  const [open, setOpen] = useState<boolean>(false);
  const [step, setStep] = useState<DeletelStep>(DeletelStep.CONFIRM);
  const [isModalVisible, setIsModalVisible] = useState<boolean>(true);

  const naivgate = useNavigate();

  const navigateToFriendList = () => {
    naivgate('/profile/friendlist');
  };

  useEffect(() => {
    if (step === DeletelStep.ALERT) {
      const timer = setTimeout(() => {
        setIsModalVisible(false);
        navigateToFriendList();
        setOpen(false);
      }, 1000);
      return () => clearTimeout(timer);
    }
  });

  const onUnfollow = () => {
    setStep(DeletelStep.ALERT);
    // 삭제할 userId 넣어주기
    mutation.mutate(userId);
  };

  const onClose = () => {
    setOpen(false);
    setStep(DeletelStep.CONFIRM);
  };

  return (
    <div>
      <Dialog open={open} onOpenChange={(open) => !open && onClose()}>
        <div className="bg-blue-300 rounded-lg px-2 py-0.5 text-sm text-white">
          <DialogTrigger onClick={() => setOpen(true)}>{title}</DialogTrigger>
        </div>
        {isModalVisible && (
          <DialogContent className="border rounded-lg bg-[#E9F2FD] mx-2 w-4/5">
            <DialogHeader>
              <DialogTitle className="flex flex-start text-color-5F86E9">
                {title}
              </DialogTitle>
            </DialogHeader>
            {step === DeletelStep.CONFIRM && (
              <div>
                <DialogDescription className="flex justify-center">
                  <h3 className="flex flex-row my-10 items-center">
                    삭제하시겠습니까?
                  </h3>
                </DialogDescription>
                <DialogFooter className="flex flex-row justify-end">
                  <Button variant="ssapick" size="md" onClick={onUnfollow}>
                    확인
                  </Button>
                </DialogFooter>
              </div>
            )}
            {step === DeletelStep.ALERT && (
              <ResultCheckModal content="삭제가 완료되었습니다." />
            )}
          </DialogContent>
        )}
      </Dialog>
    </div>
  );
};

export default DeleteModal;
