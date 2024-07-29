import PointIcon from 'icons/PointIcon';
import WarningIcon from 'icons/WarningIcon';
import DeleteIcon from 'icons/DeleteIcon';

import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTrigger,
} from 'components/ui/dialog';

import { useRef, useEffect, useState } from 'react';

interface WarningDeleteModalProps {
  warning: string;
}

const WarningDeleteModal = ({ warning }: WarningDeleteModalProps) => {
  const positionRef = useRef<HTMLButtonElement>(null);
  const [Position, setPosition] = useState({ top: 0, left: 0 });

  useEffect(() => {
    if (positionRef.current) {
      const rect = positionRef.current.getBoundingClientRect();
      setPosition({ top: rect.bottom + 50, left: rect.left - 45 });
    }
  }, []);

  return (
    <Dialog>
      <DialogTrigger ref={positionRef}>
        <PointIcon />
      </DialogTrigger>
      <DialogContent
        className="w-36 rounded-xl bg-none"
        style={{
          position: 'absolute',
          top: Position.top,
          left: Position.left,
        }}
      >
        <DialogHeader>
          <div className="flex flex-row items-center">
            <WarningIcon width={24} height={24} className="mr-3" />
            <p>{warning}</p>
          </div>
          <div className="flex flex-row items-center">
            <DeleteIcon width={24} height={24} className="mr-3" />
            <p>삭제</p>
          </div>
        </DialogHeader>
      </DialogContent>
    </Dialog>
  );
};

export default WarningDeleteModal;
