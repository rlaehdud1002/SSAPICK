import { DialogDescription } from '@radix-ui/react-dialog';
import CoinIcon from 'icons/CoinIcon';

interface CoinUseModalProps {
  coin: number;
}

const CoinUseModal = ({ coin }: CoinUseModalProps) => {
  return (
    <div>
      <DialogDescription className="flex justify-center my-10 items-center">
        <div className="flex flex-row my-5">
          <CoinIcon width={20} height={20} />
          <p className="luckiest_guy ml-1 mr-2 pt-1">{coin}</p>이 사용됩니다.
        </div>
      </DialogDescription>
    </div>
  );
};

export default CoinUseModal;
