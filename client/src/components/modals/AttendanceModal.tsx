import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from 'components/ui/dialog';

import CoinIcon from 'icons/CoinIcon';

import { useEffect, useState } from 'react';

interface AttendanceModalProps {
  date: number;
}

const AttendanceModal = ({ date }: AttendanceModalProps) => {
  const [open, setOpen] = useState<boolean>(false);

  useEffect(() => {
    const timer = setTimeout(() => {
      setOpen(false);
    }, 1000);

    return () => clearTimeout(timer);
  }, [open]);

  return (
    <Dialog open={open} onOpenChange={(open) => setOpen(open)}>
      <DialogTrigger onClick={() => setOpen(true)}>
        출석체크 모달 test
      </DialogTrigger>
      {open && (
        <DialogContent className="w-4/5 rounded-lg bg-[#E9F2FD] mx-2">
          <DialogHeader>
            <DialogTitle className="flex flex-start text-color-5F86E9">
              출석 보상
            </DialogTitle>
            <DialogDescription className="flex flex-col justify-center my-10 items-center text-color-000855 py-10">
              {(date === 7 || date === 14) && (
                <span className="bg-[#5F86E9]/70 rounded-lg text-white px-12 py-1 mb-6">
                  <b className="luckiest_guy">{date}</b>일 연속 출석 완료!
                </span>
              )}
              <h3 className="flex flex-row">
                <CoinIcon width={25} height={25} />
                <h3 className="luckiest_guy ml-1 mr-2 pt-1">1</h3>을
                획득하셨습니다!
              </h3>
            </DialogDescription>
          </DialogHeader>
        </DialogContent>
      )}
    </Dialog>
  );
};

export default AttendanceModal;
