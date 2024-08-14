import {
  DAILY_ATTENDANCE_COIN,
  TWO_WEEK_ATTENDANCE_COIN,
  WEEK_ATTENDANCE_COIN,
} from 'coins/coins';
import { Button } from 'components/ui/button';
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from 'components/ui/dialog';

import CoinIcon from 'icons/CoinIcon';

import { useEffect, useState } from 'react';

interface AttendanceModalProps {
  date: number;
  onClose: () => void;
}

const AttendanceModal = ({ date, onClose }: AttendanceModalProps) => {
  const [open, setOpen] = useState<boolean>(true);

  useEffect(() => {
    if (!open) {
      onClose();
    }
  }, [open, onClose]);

  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogContent className="w-4/5 rounded-lg bg-[#E9F2FD] mx-2">
        <DialogHeader>
          <DialogTitle className="flex flex-start text-color-5F86E9">
            출석 보상
          </DialogTitle>
          <DialogDescription className="flex flex-col justify-center my-10 items-center text-color-000855 py-10">
            <span className="bg-[#5F86E9]/70 rounded-lg text-white px-12 py-1 mb-6">
              <b className="luckiest_guy">{date}</b>일 연속 출석 완료!
            </span>
            <div className="flex flex-row">
              <CoinIcon width={25} height={25} />
              <h3 className="luckiest_guy ml-1 mr-1 pt-1">
                {date === 7
                  ? WEEK_ATTENDANCE_COIN
                  : date === 14
                    ? TWO_WEEK_ATTENDANCE_COIN
                    : DAILY_ATTENDANCE_COIN}
              </h3>
              을 획득하셨습니다!
            </div>
          </DialogDescription>
        </DialogHeader>
        <DialogFooter className="flex flex-row justify-end mt-3">
          <Button onClick={onClose} variant="ssapick" size="sm">
            닫기
          </Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
};

export default AttendanceModal;
