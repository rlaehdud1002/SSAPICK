import CoolTimeIcon from 'icons/CoolTimeIcon';

const CoolTime = () => {
  return (
    <div
      className="w-full flex flex-col items-center justify-center pb-[70px]"
      style={{ height: 'calc(100vh - 70px)' }}
    >
      <CoolTimeIcon
        width={100}
        height={100}
        className="flex items-center justify-center"
      />
      <p className="text-[20px] my-2">새로운 질문 준비중</p>
      <div className="flex flex-row items-center text-[12px]">
        <p className="luckiest_guy pt-1">11</p>분{' '}
        <p className="luckiest_guy ml-1 pt-1">19</p>초 후에{' '}
        <p className="luckiest_guy ml-1 pt-1">ssapick</p> 할 수 있어요!
      </div>
    </div>
  );
};

export default CoolTime;
