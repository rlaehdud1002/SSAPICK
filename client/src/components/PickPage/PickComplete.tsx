import CoinIcon from 'icons/CoinIcon';

const PickComplete = () => {
  return (
    <div
      className="w-full flex flex-col items-center justify-center pb-[70px]"
      style={{ height: 'calc(100vh - 70px)' }}
    >
      <CoinIcon
        width={72}
        height={72}
        className="flex items-center justify-center"
      />
      <p className="text-[20px] my-2">축하드려요!</p>
      <div className="flex flex-row items-center text-[12px]">
        <p className="luckiest_guy pt-1.5">PICKCO 40</p>개를 획득하셨어요!
      </div>
      <div className="bg-ssapick rounded-lg flex flex-row px-5 py-3 mt-16">
        눌러서 <p className="luckiest_guy pt-1.5 px-2">PICKCO</p>받기
      </div>
    </div>
  );
};

export default PickComplete;
