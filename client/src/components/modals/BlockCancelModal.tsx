import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogTitle,
  DialogTrigger,
} from "components/ui/dialog";
import { Label } from "@radix-ui/react-label";
import { Button } from "components/ui/button";
import { DialogFooter, DialogHeader } from "components/ui/dialog";
import { Input } from "components/ui/input";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { blockCancel, blockQuestionCancel } from "api/blockApi";
import { useEffect, useState } from "react";
import { set } from "react-hook-form";

interface BlockCancelModalProps {
  Id: number;
  category?: string;
}

enum BlockCancelStep {
  CHECK,
  ALERT,
}

const BlockCancelModal = ({Id, category}:BlockCancelModalProps) => {
  const [step, setStep] = useState<BlockCancelStep>(BlockCancelStep.CHECK);
  const [open, setOpen] = useState<boolean>(false);
  const queryClient = useQueryClient();

  // 유저 차단 해제
  const UserBlockCancel = useMutation({
    mutationKey: ['userBlockCancel'],
    mutationFn: blockCancel,
    
    onSuccess: () => {
      console.log('차단 해제 성공');

      if (step === BlockCancelStep.ALERT) {
        const timer = setTimeout(() => {
          queryClient.invalidateQueries({
            queryKey: ['userBlockCancel'],
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
      console.log('차단 해제 성공');
      if (step === BlockCancelStep.ALERT) {
            const timer = setTimeout(() => {
              queryClient.invalidateQueries({
                queryKey: ['deleteBlock'],
          });
              setOpen(false);
            }, 1000);
      
            return () => clearTimeout(timer);
          }

    },
  });

  
  return (
    <Dialog>
      <DialogTrigger 
      onClick={()=>{setStep(BlockCancelStep.CHECK)}}
      className="text-xs text-white bg-blue-300 px-1 py-1 rounded-lg">
        차단 해제
      </DialogTrigger>
      <DialogContent className="mx-2 w-4/5 rounded-lg">
        <DialogHeader className="flex items-start text-[#4D5BDC]">
          <DialogTitle>차단 해제</DialogTitle>
        </DialogHeader>

        {step === BlockCancelStep.CHECK && (
          <div>
        <div className="flex justify-center">
          <DialogDescription>차단을 해제 하시겠습니까?</DialogDescription>
        </div>
        <DialogFooter className="flex items-end mt-5">
          <Button onClick={()=>{
            if(category === 'user') UserBlockCancel.mutate(Id);
            else QuestionBlockCancel.mutate(Id);
            setStep(BlockCancelStep.ALERT);
            
          }} variant="ssapick" size="sm">
            해제
          </Button>
        </DialogFooter>
        </div>
      )}

      {step === BlockCancelStep.ALERT && (
        <div>
          <div className="flex justify-center">
            <DialogDescription>차단이 해제되었습니다.</DialogDescription>
          </div>
        </div>
      )}
      </DialogContent>
    </Dialog>
  );
};
export default BlockCancelModal;
