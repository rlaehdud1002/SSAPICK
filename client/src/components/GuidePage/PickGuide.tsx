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
    <div className="flex flex-col items-center my-10 p-6 bg-gray-100/50 rounded-lg shadow-lg max-w-full px-4">
      <h1 className="text-3xl font-bold text-[#5f86e9] mb-5 text-center">Pick 가이드</h1>
      <div className="flex flex-col items-center my-3 space-y-2 text-gray-700 text-center">
        <p className="whitespace-normal">랜덤한 질문이 나오고</p>
        <p className="whitespace-normal">같은 반, 친구 중에서 4명이 나옵니다</p>
        <p className="whitespace-normal">질문에 맞는 사람을 Pick!</p>
        <p className="whitespace-normal">Pick 당한 사람은 당신이 누군지 모릅니다</p>
        <p className="whitespace-normal">평소 친해지고 싶었던 사람과 친해질 수 있는 기회!</p>
      </div>
      <Carousel>
        <CarouselContent>
          <CarouselItem className="flex justify-center">
            <img width={200} src="icons/guide/Pick.png" alt="Pick" />
          </CarouselItem>
          <CarouselItem className="flex justify-center">
            <img width={200} src="icons/guide/PickDone.png" alt="Pick" />
          </CarouselItem>
          <CarouselItem className="flex justify-center">
            <img width={200} src="icons/guide/PickCoolTime.png" alt="Pick" />
          </CarouselItem>
        </CarouselContent>
        <CarouselPrevious />
        <CarouselNext />
      </Carousel>
      <div className="flex flex-col items-center my-3 space-y-2 text-gray-700 text-center">
        <p className="whitespace-normal">10개의 픽을 완료하면 픽코를 드립니다!</p>
        <p className="whitespace-normal">다음 픽을 하기위해서는 15분이 필요합니다!</p>
      </div>

      <div className="flex flex-col items-center my-3 space-y-2 text-gray-700 text-center">
        <p className="text-xl font-bold text-[#5f86e9] whitespace-normal">셔플</p>
        <p className="whitespace-normal">Pick하고 싶은 친구가 없다면 셔플을 할 수 있습니다!</p>
      </div>
      <Carousel>
        <CarouselContent>
          <CarouselItem className="flex justify-center">
            <img width={200} src="icons/guide/Pick.png" alt="Pick" />
          </CarouselItem>
          <CarouselItem className="flex justify-center">
            <img width={200} src="icons/guide/PickFriendsShuffle.png" alt="Pick" />
          </CarouselItem>
          <CarouselItem className="flex justify-center">
            <img width={200} src="icons/guide/PickFriendsShuffled.png" alt="Pick" />
          </CarouselItem>
        </CarouselContent>
        <CarouselPrevious />
        <CarouselNext />
      </Carousel>

      <div className="flex flex-col items-center my-3 space-y-2 text-gray-700 text-center">
        <p className="text-xl font-bold text-[#5f86e9] whitespace-normal">신고 / 패스</p>
        <p className="whitespace-normal">대답하고 싶지않은 질문은 패스 가능</p>
        <p className="whitespace-normal">적절하지않은 질문은 신고해주요!</p>
        <p className="whitespace-normal">패스와 차단은 총 5회 가능</p>
      </div>
      <Carousel>
        <CarouselContent>
          <CarouselItem className="flex justify-center">
            <img width={200} src="icons/guide/PickPass.png" alt="Pick" />
          </CarouselItem>
          <CarouselItem className="flex justify-center">
            <img width={200} src="icons/guide/Picks.png" alt="Pick" />
          </CarouselItem>
        </CarouselContent>
        <CarouselPrevious />
        <CarouselNext />
      </Carousel>
    </div>
  );
};

export default PickGuide;
