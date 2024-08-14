import {
  Carousel,
  CarouselContent,
  CarouselItem,
  CarouselNext,
  CarouselPrevious,
} from "components/ui/carousel";

const AlarmGuide = () => {
  return (
    <div className="flex flex-col items-center my-10 p-6 min-w-full bg-gray-100/50 rounded-lg shadow-lg max-w-full px-4">
      <h1 className="text-3xl font-bold text-[#5f86e9] mb-5 text-center">알림</h1>
      <div className="flex">
        <div className="my-5">
          <img width={200} src="icons/guide/Alarm.png" alt="" />
        </div>
        <div className="flex flex-col items-center my-3 space-y-2 text-gray-700 text-center mt-24">
          <p className="whitespace-normal">상단의 종모양 아이콘을 클릭하면,</p>
          <p className="whitespace-normal">나에게 오는 모든 알림을 확인 할 수 있어요</p>
        </div>
      </div>
    </div>
  );
};

export default AlarmGuide;
