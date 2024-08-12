import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
} from 'components/ui/dialog';
import { useEffect, useState } from 'react';

interface AlarmCheckModalProps {
  setShow: React.Dispatch<React.SetStateAction<boolean>>;
  question: string;
}

const AlarmCheckModal = ({ setShow, question }: AlarmCheckModalProps) => {
  const [open, setOpen] = useState<boolean>(true);

  useEffect(() => {
    const timer = setTimeout(() => {
      setOpen(false);
      setShow(false);
    }, 1000);
    return () => clearTimeout(timer);
  });

  const handleOpenChange = (openState: boolean) => {
    setOpen(openState);
    if (!openState) {
      setShow(false);
    }
  };

  return (
    <Dialog open={open} onOpenChange={handleOpenChange}>
      {open && (
        <DialogContent className="w-4/5 rounded-lg bg-[#E9F2FD] mx-2">
          <DialogHeader>
            <DialogTitle className="flex flex-start text-color-5F86E9">
              <span className="luckiest_guy mr-1">PICK</span>알림
            </DialogTitle>
            <DialogDescription className="flex flex-col justify-center my-10 items-center text-color-000855 py-10">
              <div className="bg-[#92AEF4]/30 text-[#4D5BDC] rounded-lg py-1 px-5 mb-4">
                {question}
              </div>
              <span>사용자가 주위에 다가오면 알림을 보내줍니다!</span>
            </DialogDescription>
          </DialogHeader>
        </DialogContent>
      )}
    </Dialog>
  );
};

export default AlarmCheckModal;
