const AttendenceGuide = () => {
  return (
    <div className="flex flex-col items-center my-10 p-6 bg-gray-100/50 rounded-lg shadow-lg max-w-full min-w-full px-4">
      <h1 className="text-3xl font-bold text-[#5f86e9] mb-5 text-center">출석</h1>
      <div className="flex flex-col items-center my-3 space-y-2 text-gray-700 text-center">
        <p className="whitespace-normal">매일 매일 출석 보상을 받을 수 있습니다!</p>
        <p className="whitespace-normal">연속 출석시 더 많은 보상이 있습니다!</p>
      </div>
      <div className="flex justify-center my-5">
        <img width={200} src="icons/guide/Attendence.png" alt="Pick" />
      </div>
    </div>
  );
};

export default AttendenceGuide;
