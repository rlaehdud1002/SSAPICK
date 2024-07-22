import PlusIcon from 'icons/PlusIcon';
import { Input } from 'components/ui/input';
import { Button } from 'components/ui/button';

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

interface QuestionPlusModalProps {
  show: boolean;
  onOpen: () => void;
  onClose: () => void;
  onCreate: () => void;
}

interface QuestionForm {
  newQuestion: string;
}

const QuestionPlusModal = ({
  show,
  onOpen,
  onClose,
  onCreate,
}: QuestionPlusModalProps) => {
  const {
    register,
    formState: { errors },
    handleSubmit,
  } = useForm<QuestionForm>();

  const onSubmit = (data: QuestionForm) => {
    console.log('ok', data);
    onCreate();
    onClose();
  };

  const onInvalid = (errors: any) => {
    console.log('errors', errors);
  };

  return (
    <form>
      <Dialog open={show} onOpenChange={(isOpen) => !isOpen && onClose()}>
        <DialogTrigger onClick={onOpen}>
          <PlusIcon />
        </DialogTrigger>
        <DialogContent className="border rounded-md bg-[#E9F2FD] mx-2 w-4/5">
          <DialogHeader>
            <DialogTitle className="flex flex-start">질문 만들기</DialogTitle>
            <DialogDescription className="flex flex-col justify-center">
              <Input
                type="text"
                className="input-box border-none h-20 focus:outline-none mt-6 mb-2"
                register={register('newQuestion', {
                  required: '질문을 입력해주세요.',
                  maxLength: {
                    value: 100,
                    message: '100글자 이하로 입력해주세요.',
                  },
                })}
              />
              <ErrorMessage
                errors={errors}
                name="newQuestion"
                render={({ message }) => <h6 className='text-red-500 text-left text-xs'>{message}</h6>}
              />
            </DialogDescription>
          </DialogHeader>
          <DialogFooter className="flex flex-row justify-end">
            <Button
              type="submit"
              variant="ssapick"
              onClick={handleSubmit(onSubmit, onInvalid)}
            >
              질문 생성
            </Button>
          </DialogFooter>
        </DialogContent>
      </Dialog>
    </form>
  );
};

export default QuestionPlusModal;
