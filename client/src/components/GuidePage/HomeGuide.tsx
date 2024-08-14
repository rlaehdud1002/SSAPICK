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
      <h1 className="text-3xl font-bold text-[#5f86e9] mb-5 text-center">메인 페이지</h1>
      <div className="flex">
        <div className="flex flex-col items-center mt-24 mb-3 space-y-2 text-gray-700 text-center mx-10">
          <p className="text-pretty">당신이 지목받은 질문의 리스트가 나오고,</p>
          <p className="text-pretty">픽코를 사용해서 </p>
          <p className="text-pretty">나를 지목한 친구에 대한 힌트를 오픈!</p>
        </div>
        <Carousel>
          <CarouselContent>
            <CarouselItem className="flex justify-center">
              <img width={200} src="icons/guide/HomeHintOpenOne.png" alt="Home" />
            </CarouselItem>
            <CarouselItem className="flex justify-center">
              <img width={200} src="icons/guide/HomeHintOpenModal.png" alt="Home" />
            </CarouselItem>
            <CarouselItem className="flex justify-center">
              <img width={200} src="icons/guide/Home.png" alt="Home" />
            </CarouselItem>
          </CarouselContent>
          {/* <CarouselPrevious />
        <CarouselNext /> */}
        </Carousel>
      </div>

      <p className="text-xl font-bold text-[#5f86e9] mt-5">쪽지 전송</p>
      <div className="flex">
        <Carousel>
          <CarouselContent>
            <CarouselItem className="flex justify-center">
              <img width={200} src="icons/guide/MakeMessage.png" alt="Home" />
            </CarouselItem>

            <CarouselItem className="flex justify-center">
              <img width={200} src="icons/guide/SendMessage.png" alt="Home" />
            </CarouselItem>

            <CarouselItem className="flex justify-center">
              <img width={200} src="icons/guide/JudgeMessage.png" alt="Home" />
            </CarouselItem>
          </CarouselContent>
          {/* <CarouselPrevious />
        <CarouselNext /> */}
        </Carousel>
        <div className="flex flex-col items-center my-3 space-y-2 text-gray-700 text-center mx-10 mt-20">
          <p className="text-pretty">내가 지목받은 질문에 픽해준 </p>
          <p className="text-pretty">친구가 궁금하다면</p>
          <p className="text-pretty">픽코를 사용해서 쪽지를 전송할 수 있습니다!</p>
          <p className="whitespace-normal mt-5">AI가 문장을 판단해서 불건전한 내용은 걸러줘요 !</p>
        </div>
      </div>
    </div>
  );
};

export default HomeGuide;
