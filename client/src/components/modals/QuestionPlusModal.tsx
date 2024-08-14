import PlusIcon from 'icons/PlusIcon';
import { Button } from 'components/ui/button';

import { useState } from 'react';
import { useForm } from 'react-hook-form';

import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
  DialogFooter,
} from 'components/ui/dialog';

import SelectCategory from 'components/PickPage/SelectCategory';
import InputModal from 'components/modals/InputModal';
import ResultCheckModal from 'components/modals/ResultCheckModal';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { postCreateQuestion } from 'api/questionApi';
import { ICreateQuestion } from 'atoms/Pick.type';

enum NewQuestionStep {
  INPUT,
  ALERT,
}

interface QuestionForm {
  category: string;
  newQuestion: string;
}

interface QuestionPlusModalProps {
  location: string;
}

const QuestionPlusModal = ({ location }: QuestionPlusModalProps) => {
  const [step, setStep] = useState<NewQuestionStep>(NewQuestionStep.INPUT);
  const [open, setOpen] = useState<boolean>(false);

  // 질문 생성 api
  const queryClient = useQueryClient();
  const mutation = useMutation({
    mutationKey: ['create'],
    mutationFn: postCreateQuestion,

    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['questions'] });
    },
  });

  const {
    register,
    formState: { errors },
    handleSubmit,
    reset,
    setValue,
  } = useForm<QuestionForm>();

  const onSubmit = (data: QuestionForm) => {
    const createData: ICreateQuestion = {
      categoryId: parseInt(data.category),
      content: data.newQuestion,
    };

    mutation.mutate(createData);

    setStep(NewQuestionStep.ALERT);
    setTimeout(() => {
      setOpen(false);
      setStep(NewQuestionStep.INPUT);
    }, 1000);
    reset();
  };

  const onInvalid = (errors: any) => {
    console.log('errors', errors);
  };

  return (
    <form className="flex items-center">
      <Dialog open={open} onOpenChange={(open) => setOpen(open)}>
        <DialogTrigger onClick={() => setOpen(true)} className="w-full">
          {location === 'pickpage' ? (
            <div className="bg-[#5F86E9]/50 rounded-full px-2 py-1 flex flex-row items-center text-sm">
              <PlusIcon className="mr-1" />
              질문 생성
            </div>
          ) : (
            <div className="bg-ssapick rounded-lg text-center mt-4 p-4">
              질문 생성하기!
            </div>
          )}
        </DialogTrigger>
        {open && (
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
                <InputModal
                  name="newQuestion"
                  register={register('newQuestion', {
                    required: '질문을 입력해주세요.',
                    maxLength: {
                      value: 100,
                      message: '100글자 이하로 입력해주세요.',
                    },
                    minLength: {
                      value: 5,
                      message: '5글자 이상 입력해주세요.',
                    },
                  })}
                  errors={errors}
                />
                <DialogFooter className="flex flex-row justify-end mt-2">
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
            {step === NewQuestionStep.ALERT && (
              <ResultCheckModal content="질문 생성 신청이 완료되었습니다." />
            )}
          </DialogContent>
        )}
      </Dialog>
    </form>
  );
};

export default QuestionPlusModal;
