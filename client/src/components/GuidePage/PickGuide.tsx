import React from "react";

import {
  Carousel,
  CarouselContent,
  CarouselItem,
  CarouselNext,
  CarouselPrevious,
} from "components/ui/carousel";

const PickGuide = () => {
  return (
    <div className="flex flex-col items-center my-10 p-max-w-full min-w-full space-y-10">
      <div className="flex">
        <Carousel>
          <CarouselContent>
            <CarouselItem className="flex justify-center">
              <img width={200} height={400} src="icons/guide/Pick.png" alt="Pick" />
            </CarouselItem>
            <CarouselItem className="flex justify-center">
              <img width={200} height={400} src="icons/guide/PickDone.png" alt="Pick" />
            </CarouselItem>
            <CarouselItem className="flex justify-center">
              <img width={200} height={400} src="icons/guide/PickCoolTime.png" alt="Pick" />
            </CarouselItem>
          </CarouselContent>
          {/* <CarouselPrevious />
        <CarouselNext /> */}
        </Carousel>
        <div className="flex min-w-[140px] justify-center flex-col items-center space-y-1 text-gray-700 text-center mx-2">
          <p className="text-prettty text-xs">랜덤한 질문과</p>
          <p className="text-prettty text-xs pb-2">나의 친구 중 4명 등장!</p>

          <p className="text-prettty text-xs">질문에 일치하는 친구를</p>
          <p className="text-prettty text-md font-lucky text-[#5f86e9] pb-2">SSAPICK !</p>

          <p className="text-prettty text-xs">선택받은 사람은</p>
          <p className="text-prettty text-xs pb-2">힌트를 사용해 나를 추측!</p>

          <p className="text-prettty text-xs">아직 어색한 친구들과</p>
          <p className="text-prettty text-xs">SSAPICK을 사용해</p>
          <p className="text-prettty text-xs">어색함을 풀어보세요!</p>
        </div>
      </div>

      <div className="flex">
      <div className="flex min-w-[140px] justify-center flex-col items-center space-y-1 text-gray-700 text-center mx-2">
          <p className="text-prettty text-xs">일치하는 친구가 없다면</p>
          <p className="text-prettty text-xs pb-2">새로운 친구들로 셔플!</p>
        </div>
        <Carousel>
          <CarouselContent>
            <CarouselItem className="flex justify-center">
              <img width={200} height={400} src="icons/guide/Pick.png" alt="Pick" />
            </CarouselItem>
            <CarouselItem className="flex justify-center">
              <img width={200} height={400} src="icons/guide/PickFriendsShuffle.png" alt="Pick" />
            </CarouselItem>
            <CarouselItem className="flex justify-center">
              <img width={200} height={400} src="icons/guide/PickFriendsShuffled.png" alt="Pick" />
            </CarouselItem>
          </CarouselContent>
          {/* <CarouselPrevious />
        <CarouselNext /> */}
        </Carousel>
      </div>
      <div className="flex">
        <Carousel>
          <CarouselContent>
            <CarouselItem className="flex justify-center">
              <img width={200} height={400} src="icons/guide/PickPass.png" alt="Pick" />
            </CarouselItem>
            <CarouselItem className="flex justify-center">
              <img width={200} height={400} src="icons/guide/Picks.png" alt="Pick" />
            </CarouselItem>
          </CarouselContent>
          {/* <CarouselPrevious />
        <CarouselNext /> */}
        </Carousel>
        <div className="flex min-w-[140px] justify-center flex-col items-center space-y-1 text-gray-700 text-center mx-2">
          <p className="text-pretty text-xs">대답하고 싶지않은</p>
          <p className="text-pretty text-xs pb-2">질문은 패스 가능</p>
          <p className="text-pretty text-xs">적절하지않은</p>
          <p className="text-pretty text-xs pb-2">질문은 신고해주요!</p>
          <p className="text-pretty text-xs">패스와 차단은</p>
          <p className="text-pretty text-xs">총 <strong className="font-lucky text-lg">5 </strong>회 가능</p>
        </div>
      </div>
    </div>
  );
};

export default PickGuide;
