import { useMutation, useQueryClient } from '@tanstack/react-query';
import { blockCancel, blockQuestionCancel } from 'api/blockApi';
import { Button } from 'components/ui/button';
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from 'components/ui/dialog';
import { useState } from 'react';

interface BlockCancelModalProps {
  Id: number;
  category?: string;
}

enum BlockCancelStep {
  CHECK,
  ALERT,
}

const BlockCancelModal = ({ Id, category }: BlockCancelModalProps) => {
  const [step, setStep] = useState<BlockCancelStep>(BlockCancelStep.CHECK);
  const [open, setOpen] = useState<boolean>(false);
  const queryClient = useQueryClient();

  // 유저 차단 해제
  const UserBlockCancel = useMutation({
    mutationKey: ['userBlockCancel'],
    mutationFn: blockCancel,

    onSuccess: () => {
      if (step === BlockCancelStep.ALERT) {
        setTimeout(() => {
          queryClient.invalidateQueries({
            queryKey: ['blocks'],
          });
          setOpen(false);
        }, 1000);
      }
    },
  });

  // 질문 차단 해제
  const QuestionBlockCancel = useMutation({
    mutationKey: ['deleteBlock'],
    mutationFn: blockQuestionCancel,

    onSuccess: () => {
      if (step === BlockCancelStep.ALERT) {
        setTimeout(() => {
          queryClient.invalidateQueries({
            queryKey: ['blockQuestion'],
          });
          setOpen(false);
        }, 1000);
      }
    },
  });

  return (
    <Dialog open={open} onOpenChange={(open) => setOpen(open)}>
      <DialogTrigger
        onClick={() => {
          setStep(BlockCancelStep.CHECK);
          setOpen(true);
        }}
        className="text-xs text-white bg-blue-300 px-1 py-1 rounded-lg"
      >
        차단 해제
      </DialogTrigger>
      <DialogContent className="mx-2 w-4/5 rounded-lg">
        <DialogHeader className="flex items-start text-[#5F86E9]">
          <DialogTitle>차단 해제</DialogTitle>
        </DialogHeader>

        {step === BlockCancelStep.CHECK && (
          <div>
            <div className="flex justify-center my-12">
              <DialogDescription className="text-color-000855 text-[16px]">
                차단을 해제하시겠습니까?
              </DialogDescription>
            </div>
            <DialogFooter className="flex items-end">
              <Button
                onClick={() => {
                  if (category === 'user') UserBlockCancel.mutate(Id);
                  else QuestionBlockCancel.mutate(Id);
                  setStep(BlockCancelStep.ALERT);
                }}
                variant="ssapick"
                size="sm"
              >
                해제
              </Button>
            </DialogFooter>
          </div>
        )}

        {step === BlockCancelStep.ALERT && (
          <div>
            <div className="flex justify-center my-5">
              <DialogDescription>차단이 해제되었습니다.</DialogDescription>
            </div>
          </div>
        )}
      </DialogContent>
    </Dialog>
  );
};
export default BlockCancelModal;
