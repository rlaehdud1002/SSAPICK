import { useEffect, useState } from 'react';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { deleteReceivedMessage, deleteSendMessage } from 'api/messageApi';
import ResultCheckModal from 'components/modals/ResultCheckModal';
import DeleteIcon from 'icons/DeleteIcon';
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
import { blockUser } from 'api/blockApi';
import { useNavigate } from 'react-router-dom';

enum WarningDeleteStep {
  CHECK,
  ALERT,
}

interface WarningDeleteModalProps {
  senderId: number;
  messageId: number;
  title: string;
  message: string;
  location: string;
  setPopoverOpen: React.Dispatch<React.SetStateAction<boolean>>;
}

const WarningDeleteModal = ({
  senderId,
  messageId,
  title,
  message,
  location,
  setPopoverOpen,
}: WarningDeleteModalProps) => {
  const [step, setStep] = useState<WarningDeleteStep>(WarningDeleteStep.CHECK);
  const [open, setOpen] = useState<boolean>(false);
  const [isModalVisible, setIsModalVisible] = useState<boolean>(true);

  const queryClient = useQueryClient();
  const nav = useNavigate();

  // 유저 차단 api
  const blockMutatiion = useMutation({
    mutationKey: ['block', 'user'],
    mutationFn: blockUser,

    onSuccess: () => {
      console.log('쪽지 차단 성공');
      queryClient.invalidateQueries({
        queryKey: ['message', 'send'],
      });
    },
  });

  // 쪽지 삭제 mutation
  const deleteMutation = useMutation({
    mutationKey: ['message', 'delete'],
    mutationFn: ({
      messageId,
      location,
    }: {
      messageId: number;
      location: string;
    }) => {
      if (location === 'send') {
        return deleteSendMessage(messageId); // Promise를 반환
      } else if (location === 'received') {
        console.log('받은 메시지 삭제 method 들어옴');
        return deleteReceivedMessage(messageId); // Promise를 반환
      } else {
        throw new Error('Invalid location');
      }
    },

    // 쪽지 삭제 후 쪽지 목록 새로 고침
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ['message', location === 'send' ? 'send' : 'received'],
      });
    },
  });

  // 마지막 모달이 실행된 후 1초 뒤 자동으로 닫힘
  useEffect(() => {
    if (step === WarningDeleteStep.ALERT) {
      const timer = setTimeout(() => {
        setIsModalVisible(false);
      }, 500);

      return () => clearTimeout(timer);
    }
  }, [step]);

  const onClick = () => {
    if (title === '차단') {
      blockMutatiion.mutate(senderId);
    } else {
      console.log('메시지 삭제', location);
      deleteMutation.mutate({ messageId, location });
    }

    setStep(WarningDeleteStep.ALERT);
    setPopoverOpen(false);
  };

  const onClose = () => {
    setOpen(false);
    setStep(WarningDeleteStep.CHECK);
  };

  return (
    <Dialog open={open} onOpenChange={(open) => !open && onClose()}>
      <DialogTrigger onClick={() => setOpen(true)}>
        <div className="flex flex-row">
          {title === '차단' ? (
            <WarningIcon width={24} height={24} className="mr-3" />
          ) : (
            <DeleteIcon width={24} height={24} className="mr-3" />
          )}
          <span>{title}</span>
        </div>
      </DialogTrigger>
      {isModalVisible && (
        <DialogContent className="border rounded-lg bg-[#E9F2FD] mx-2 w-4/5">
          <DialogHeader>
            <DialogTitle className="flex flex-start text-color-5F86E9">
              쪽지 {title}
            </DialogTitle>
          </DialogHeader>
          {step === WarningDeleteStep.CHECK && (
            <div>
              <div className="flex flex-col items-center my-16 text-center">
                <p>이 쪽지를 {title}하시겠습니까?</p>
                {title === '차단' && (
                  <p className="bg-[#92AEF4]/30 rounded-lg text-[#4D5BDC] w-4/5 p-1 mt-3">
                    {message}
                  </p>
                )}
              </div>
              <DialogFooter className="flex flex-row justify-end">
                <Button
                  type="submit"
                  className="bg-[#E95F5F] hover:bg-red-400 "
                  onClick={onClick}
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
