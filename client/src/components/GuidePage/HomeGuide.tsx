import {
  Carousel,
  CarouselContent,
  CarouselItem,
  CarouselNext,
  CarouselPrevious,
} from "components/ui/carousel";

const HomeGuide = () => {
  return (
    <div className="flex flex-col items-center min-w-full my-10 p-6 bg-gray-100/50 rounded-lg shadow-lg max-w-full px-4">
      <h1 className="text-3xl font-bold text-[#5f86e9] mb-5 text-center">
        Home 가이드
      </h1>
      <div className="flex flex-col items-center my-3 space-y-2 text-gray-700 text-center">
        <p className="whitespace-normal">당신이 지목받은 질문의 리스트가 나오고</p>
        <p className="whitespace-normal">픽코를 사용해서, </p>
        <p className="whitespace-normal">나를 지목한 친구에 대한 힌트를 오픈!</p>
      </div>
      <Carousel>
        <CarouselContent>
          <CarouselItem className="flex justify-center">
            <img width={200} src="icons/guide/HomeHintOpenOne.png" alt="Home" />
          </CarouselItem>
          <CarouselItem className="flex justify-center">
            <img
              width={200}
              src="icons/guide/HomeHintOpenModal.png"
              alt="Home"
            />
          </CarouselItem>
          <CarouselItem className="flex justify-center">
            <img width={200} src="icons/guide/Home.png" alt="Home" />
          </CarouselItem>
        </CarouselContent>
        <CarouselPrevious />
        <CarouselNext />
      </Carousel>
    </div>
  );
};

export default HomeGuide;
