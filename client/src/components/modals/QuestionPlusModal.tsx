import PlusIcon from 'icons/PlusIcon';
import { Button } from 'components/ui/button';

import { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';

import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
  DialogFooter,
} from 'components/ui/dialog';

import QuestionInput from 'components/modals/QuestionInput';
import QuestionCheckModal from 'components/modals/QuestionCheckModal';
import SelectCategory from 'components/PickPage/SelectCategory';

enum NewQuestionStep {
  INPUT,
  ALERT,
}

interface QuestionForm {
  category: string;
  newQuestion: string;
}

const QuestionPlusModal = () => {
  const [step, setStep] = useState<NewQuestionStep>(NewQuestionStep.INPUT);
  const [open, setOpen] = useState<boolean>(false);
  const [isModalVisible, setIsModalVisible] = useState<boolean>(true);

  // 마지막 모달이 실행된 후 1초 뒤 자동으로 닫힘
  useEffect(() => {
    if (step === NewQuestionStep.ALERT) {
      const timer = setTimeout(() => {
        setIsModalVisible(false);
      }, 1000);

      return () => clearTimeout(timer);
    }
  }, [step]);

  const {
    register,
    formState: { errors },
    handleSubmit,
    reset,
    setValue,
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
        {isModalVisible && (
          <DialogContent className="border rounded-lg bg-[#E9F2FD] mx-2 w-4/5">
            <DialogHeader>
              <DialogTitle className="flex flex-start text-color-5F86E9">
                질문 만들기
              </DialogTitle>
            </DialogHeader>
            {step === NewQuestionStep.INPUT && (
              <div>
                <SelectCategory
                  name="category"
                  title="카테고리"
                  register={register('category', {
                    required: '카테고리를 선택해주세요.',
                  })}
                  setValue={(value: string) => setValue('category', value)}
                  errors={errors}
                />
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
        )}
      </Dialog>
    </form>
  );
};

export default QuestionPlusModal;
