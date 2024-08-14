import {
  Carousel,
  CarouselContent,
  CarouselItem,
  CarouselNext,
  CarouselPrevious,
} from "components/ui/carousel";

const MessageGuide = () => {
  return (
    <div className="flex flex-col items-center my-10 p-6 bg-gray-100/50 rounded-lg shadow-lg max-w-full min-w-full px-4">
      <h1 className="text-3xl font-bold text-[#5f86e9] mb-5 text-center">쪽지</h1>
      <div className="flex">
        <div className="flex flex-col items-center my-3 space-y-2 text-gray-700 text-center mx-7 mt-24">
          <p className="whitespace-normal">메세지를 보낼 수 있고 받을 수 있습니다!</p>
          <p className="whitespace-normal">(메세지에는 답장이 불가 합니다 ㅠㅠ)</p>
        </div>
        <Carousel className="my-5">
          <CarouselContent>
            <CarouselItem className="flex justify-center">
              <img width={200} src="icons/guide/Message.png" alt="Pick" />
            </CarouselItem>
            <CarouselItem className="flex justify-center">
              <img width={200} src="icons/guide/MessageSend.png" alt="Pick" />
            </CarouselItem>
          </CarouselContent>
          {/* <CarouselPrevious />
        <CarouselNext /> */}
        </Carousel>
      </div>
    </div>
  );
};

export default MessageGuide;
