import {
  Carousel,
  CarouselContent,
  CarouselItem,
  CarouselNext,
  CarouselPrevious,
} from "components/ui/carousel";

const LocationGuide = () => {
  return (
    <div className="flex flex-col items-center my-10 p-6 bg-gray-100/50 rounded-lg shadow-lg max-w-full min-w-full px-4">
      <h1 className="text-3xl font-bold text-[#5f86e9] mb-5 text-center">내 주변 알림</h1>
      <div className="flex">
        <Carousel className="my-5">
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
        <div className="flex flex-col items-center my-3 space-y-2 text-gray-700 text-center mx-7 mt-24">
          <p className="whitespace-normal">위치를 기반으로 주변 사용자를 볼 수 있습니다!</p>
          <p className="whitespace-normal">그 사용자를 잡으면 PickCo Get!</p>
        </div>
      </div>
    </div>
  );
};

export default LocationGuide;
