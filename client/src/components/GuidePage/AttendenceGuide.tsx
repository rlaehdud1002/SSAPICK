const AttendenceGuide = () => {
  return (
    <div className="flex flex-col items-center min-w-full max-w-full px-4 my-10">
      <div className="flex">
        <div className="flex min-w-[140px] flex-col items-center space-y-1 text-gray-700 text-center justify-center mx-2">
          <p className="text-pretty text-xs">매일 매일 출석을</p>
          <p className="text-pretty text-xs">꾸준히 참여하면</p>
          <p className="text-pretty text-xs">많은 <strong className="font-lucky text-[#5f86e9] text-lg">PICKCO</strong></p>
          <p className="text-pretty text-xs">획득의 기회가!</p>
        </div>
        <div>
          <img width={200} height={400} src="icons/guide/Attendence.png" alt="Pick" />
        </div>
      </div>
    </div>
  );
};

export default AttendenceGuide;
