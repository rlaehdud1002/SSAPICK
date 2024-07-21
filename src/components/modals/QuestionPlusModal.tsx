import PlusIcon from 'icons/PlusIcon';
import { Input } from 'components/ui/input';
import { Button } from 'components/ui/button';

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

const QuestionPlusModal = ({
  show,
  onOpen,
  onClose,
  onCreate,
}: QuestionPlusModalProps) => {
  return (
    <div>
      <Dialog open={show} onOpenChange={(isOpen) => !isOpen && onClose()}>
        <DialogTrigger onClick={onOpen}>
          <PlusIcon />
        </DialogTrigger>
        <DialogContent className="border rounded-md bg-[#E9F2FD] mx-2 w-4/5">
          <DialogHeader>
            <DialogTitle className="flex flex-start">질문 만들기</DialogTitle>
            <DialogDescription className="flex justify-center">
              <Input
                type="text"
                className="input-box border-none h-20 focus:outline-none mt-6"
              />
            </DialogDescription>
          </DialogHeader>
          <DialogFooter className="flex flex-row justify-end">
            <Button
              variant="ssapick"
              onClick={() => {
                onCreate();
                onClose();
              }}
            >
              질문 생성
            </Button>
          </DialogFooter>
        </DialogContent>
      </Dialog>
    </div>
  );
};

export default QuestionPlusModal;
