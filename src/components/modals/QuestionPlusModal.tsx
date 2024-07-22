import PlusIcon from 'icons/PlusIcon';
import { Input } from 'components/ui/input';
import { Button } from 'components/ui/button';

import { useState } from 'react';
import { useForm } from 'react-hook-form';
import { ErrorMessage } from '@hookform/error-message';

import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
  DialogFooter,
} from 'components/ui/dialog';

import QuestionInput from 'components/modals/QuestionInput';
import Question from 'components/PickPage/Question';
import QuestionCheckModal from 'components/modals/QuestionCheckModal';

enum NewQuestionStep {
  INPUT,
  ALERT,
}

interface QuestionForm {
  newQuestion: string;
}

const QuestionPlusModal = () => {
  const [step, setStep] = useState<NewQuestionStep>(NewQuestionStep.INPUT);
  const [open, setOpen] = useState<boolean>(false);

  const {
    register,
    formState: { errors },
    handleSubmit,
    reset,
  } = useForm<QuestionForm>();

  const onSubmit = (data: QuestionForm) => {
    console.log('ok', data);
    setStep(NewQuestionStep.ALERT);
    reset();
  };

  const onInvalid = (errors: any) => {
    console.log('errors', errors);
  };

  const onClose = () => {
    setOpen(false);
    setStep(NewQuestionStep.INPUT);
  };

  return (
    <form>
      <Dialog open={open} onOpenChange={(open) => !open && onClose()}>
        <DialogTrigger onClick={() => setOpen(true)}>
          <PlusIcon />
        </DialogTrigger>
        <DialogContent className="border rounded-md bg-[#E9F2FD] mx-2 w-4/5">
          <DialogHeader>
            <DialogTitle className="flex flex-start">질문 만들기</DialogTitle>
          </DialogHeader>
          {step === NewQuestionStep.INPUT && (
            <div>
              <QuestionInput
                register={register('newQuestion', {
                  required: '질문을 입력해주세요.',
                  maxLength: {
                    value: 100,
                    message: '100글자 이하로 입력해주세요.',
                  },
                })}
                errors={errors}
              />
              <DialogFooter className="flex flex-row justify-end">
                <Button
                  type="submit"
                  variant="ssapick"
                  onClick={handleSubmit(onSubmit, onInvalid)}
                >
                  질문 생성
                </Button>
              </DialogFooter>
            </div>
          )}
          {step === NewQuestionStep.ALERT && <QuestionCheckModal />}
        </DialogContent>
      </Dialog>
    </form>
  );
};

export default QuestionPlusModal;
