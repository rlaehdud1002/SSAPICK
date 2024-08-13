import {
  Carousel,
  CarouselContent,
  CarouselItem,
  CarouselNext,
  CarouselPrevious
} from "components/ui/carousel";
import MainpageGuide from "icons/MainpageGuide";
import MainpageModal from "icons/MainpageModal";

const HomeGuide = () => {
  return (
    <div className="mt-10">
      <div className="mb-5">
        <span className="text-3xl">메인 페이지</span>
        <p>
          <br />코인을 사용하여 나를 픽한 친구의 힌트를 확인할 수 있어요.
        </p>
      </div>
      <Carousel className="bg-white/50 rounded-lg h-[550px]">
        <CarouselContent>

          <CarouselItem className="flex flex-col items-center justify-center">
            <p>내가 받은 픽의 리스트를 확인할 수 있어요!</p>
            <MainpageGuide />
          </CarouselItem>

          <CarouselItem>
            <MainpageModal />
          </CarouselItem>

          <CarouselItem>third</CarouselItem>

        </CarouselContent>
        <CarouselPrevious />
        <CarouselNext />
      </Carousel>
    </div >
  )
};

export default HomeGuide;
