import {
  Carousel,
  CarouselContent,
  CarouselItem,
  CarouselNext,
  CarouselPrevious,
} from "components/ui/carousel";

const LocationGuide = () => {
  return (
    <div className="flex flex-col items-center max-w-full min-w-full px-4">
      <div className="flex">
        <Carousel>
          <CarouselContent>
            <CarouselItem className="flex justify-center">
              <img width={200} height={400} src="icons/guide/Location.png" alt="Pick" />
            </CarouselItem>
            <CarouselItem className="flex justify-center">
              <img width={200} height={400} src="icons/guide/LocationPickco.png" alt="Pick" />
            </CarouselItem>
            <CarouselItem className="flex justify-center">
              <img width={200} height={400} src="icons/guide/LocationCatchOne.png" alt="Pick" />
            </CarouselItem>
            <CarouselItem className="flex justify-center">
              <img width={200} height={400} src="icons/guide/LocationPickco.png" alt="Pick" />
            </CarouselItem>
            <CarouselItem className="flex justify-center">
              <img width={200} height={400} src="icons/guide/LocationCatchTwo.png" alt="Pick" />
            </CarouselItem>
          </CarouselContent>
          {/* <CarouselPrevious />
        <CarouselNext /> */}
        </Carousel>
        <div className="flex min-w-[140px] flex-col items-center space-y-1 text-gray-700 text-center mx-2 justify-center">
        <p className="text-pretty text-xs">위치를 기반으로</p>
        <p className="text-pretty text-xs">주변 사용자를</p>
        <p className="text-pretty text-xs pb-2">찾아볼 수 있습니다!</p>

        <p className="text-pretty text-xs">내 주위 사용자를</p>
        <p className="text-pretty text-xs"><strong className="font-lucky text-[#5f86e9] text-lg">PICK </strong>하여</p>
        <p className="text-pretty text-xs">픽코를 얻어보세요.</p>
        </div>
      </div>
    </div>
  );
};

export default LocationGuide;
