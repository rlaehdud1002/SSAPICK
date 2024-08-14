const AlarmGuide = () => {
  return (
    <div className="flex flex-col items-center min-w-full max-w-full px-4">
      <div className="flex">
        <div>
          <img width={200} height={400} src="icons/guide/Alarm.png" alt="" />
        </div>
        <div className="flex min-w-[140px] flex-col items-center space-y-1 text-gray-700 text-center justify-center mx-2">
          <p className="text-pretty text-xs">헤더의 종을 누르면 </p>
          <p className="text-pretty text-xs">지금까지 나에  게 온</p>
          <p className="text-pretty text-xs">알람을 확인할 수 있어요!</p>
        </div>
      </div>
    </div>
  );
};

export default AlarmGuide;
