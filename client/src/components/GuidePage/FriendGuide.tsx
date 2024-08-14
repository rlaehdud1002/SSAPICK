import { Carousel, CarouselContent, CarouselItem } from "components/ui/carousel";

const FriendGuide = () => {
  return (
    <div className="flex flex-col items-center my-10 max-w-full min-w-full px-4">
      <div className="flex">
        <div className="flex min-w-[140px] flex-col justify-center items-center space-y-1 text-gray-700 text-center">
          <p className="text-pretty text-xs">나의 친구들을</p>
          <p className="text-pretty text-xs pb-4">확인해보세요.</p>

          <p className="text-pretty text-xs">같은 반 학우들은</p>
          <p className="text-pretty text-xs pb-4">반 친구로 추가됩니다.</p>

          <p className="text-pretty text-xs">내가 알만한 친구 추천과</p>
          <p className="text-pretty text-xs">키워드를 검색하여</p>
          <p className="text-pretty text-xs pb-4">친구를 추가하세요!</p>
        </div>
        <Carousel>
          <CarouselContent>
            <CarouselItem className="flex justify-center">
            <img width={200} height={400} src="icons/guide/FriendsList.png" alt="Pick" />
            </CarouselItem>
            <CarouselItem className="flex justify-center">
            <img width={200} height={400} src="icons/guide/SearchFriends.png" alt="Pick" />
            </CarouselItem>
          </CarouselContent>
          {/* <CarouselPrevious />
        <CarouselNext /> */}
        </Carousel>
      </div>
    </div>
  );
};

export default FriendGuide;
