import { DialogDescription } from '@radix-ui/react-dialog';
import CoinIcon from 'icons/CoinIcon';

const CoinUseModal = () => {
  return (
    <div>
      <DialogDescription className="flex justify-center my-10 items-center">
        <h3 className="flex flex-row my-10">
          <CoinIcon width={25} height={25} />
          <h3 className="luckiest_guy ms-1 me-2 pt-1">1</h3>이 사용됩니다.
        </h3>
      </DialogDescription>
    </div>
  );
};

export default CoinUseModal;
