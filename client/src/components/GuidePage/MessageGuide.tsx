import {
  Carousel,
  CarouselContent,
  CarouselItem,
  CarouselNext,
  CarouselPrevious,
} from "components/ui/carousel";

const MessageGuide = () => {
  return (
    <div className="flex flex-col items-center my-10 max-w-full min-w-full px-4">
      <div className="flex">
        <div className="flex min-w-[140px] flex-col justify-center items-center space-y-1 text-gray-700 text-center mx-2">
          <p className="text-pretty text-xs">힌트이외에도</p>
          <p className="text-pretty text-xs">쪽지를 보내면서</p>
          <p className="text-pretty text-xs pb-10">알아갈 수 있습니다.</p>

          <p className="text-pretty text-xs">쪽지에 답장은</p>
          <p className="text-pretty text-xs">할 수 없습니다.</p>
        </div>
        <Carousel>
          <CarouselContent>
            <CarouselItem className="flex justify-center">
              <img width={200} height={400} src="icons/guide/Message.png" alt="Pick" />
            </CarouselItem>
            <CarouselItem className="flex justify-center">
              <img width={200} height={400} src="icons/guide/MessageSend.png" alt="Pick" />
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
