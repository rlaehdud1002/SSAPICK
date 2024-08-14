import {
  Carousel,
  CarouselContent,
  CarouselItem,
  CarouselNext,
  CarouselPrevious,
} from "components/ui/carousel";

const HomeGuide = () => {
  return (
    <div className="flex flex-col items-center min-w-full my-10 space-y-10">
      <div className="flex ">
        <div className="flex min-w-[140px] justify-center flex-col items-center space-y-1 text-gray-700 text-center mx-2">
          <p className="text-xs text-pretty">받은 질문들을 힌트를</p>
          <p className="text-xs text-pretty">사용해 보낸 사람을</p>
          <p className="text-xs text-pretty">추측해보세요.</p>
        </div>
        <Carousel>
          <CarouselContent>
            <CarouselItem className="flex justify-center">
              <img width={200} height={400} src="icons/guide/HomeHintOpenOne.png" alt="Home" />
            </CarouselItem>
            <CarouselItem className="flex justify-center">
              <img width={200} height={400} src="icons/guide/HomeHintOpenModal.png" alt="Home" />
            </CarouselItem>
            <CarouselItem className="flex justify-center">
              <img width={200} height={400} src="icons/guide/Home.png" alt="Home" />
            </CarouselItem>
          </CarouselContent>
        </Carousel>
      </div>
      <div className="flex">
        <Carousel>
          <CarouselContent>
            <CarouselItem className="flex justify-center">
              <img width={200} height={400} src="icons/guide/MakeMessage.png" alt="Home" />
            </CarouselItem>

            <CarouselItem className="flex justify-center">
              <img width={200} height={400} src="icons/guide/SendMessage.png" alt="Home" />
            </CarouselItem>

            <CarouselItem className="flex justify-center">
              <img width={200} height={400} src="icons/guide/JudgeMessage.png" alt="Home" />
            </CarouselItem>
          </CarouselContent>
          {/* <CarouselPrevious />
        <CarouselNext /> */}
        </Carousel>
        <div className="flex min-w-[150px] flex-col items-center justify-center space-y-1 text-gray-700 text-center mx-2">
          <p className="text-xs text-pretty">또한, 쪽지를 이용하면</p>
          <p className="text-xs text-pretty">나를 선택한 사람에게</p>
          <p className="text-xs text-pretty pb-4">바로 메시지를 전송!</p>
          <p className="text-xs text-pretty">AI를 사용해 욕설 검사까지!</p>
        </div>
      </div>
    </div>
  );
};

export default HomeGuide;
