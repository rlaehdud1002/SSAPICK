import React from "react";

import {
  Carousel,
  CarouselContent,
  CarouselItem,
  CarouselNext,
  CarouselPrevious,
} from "components/ui/carousel";
import PickGuideIcon from "icons/PickGuideIcon";

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
            <PickGuideIcon width={200} height={500} />
          </CarouselItem>
          <CarouselItem>second</CarouselItem>\
        </CarouselContent>
        <CarouselPrevious />
        <CarouselNext />
      </Carousel>
      <div className="flex flex-col items-center my-3 space-y-2 text-gray-700 text-center">
        <p className="whitespace-normal">대답하고 싶지않은 질문은 패스 가능</p>
        <p className="whitespace-normal">적절하지않은 질문은 신고해주요!</p>
        <p className="whitespace-normal">패스와 차단은 총 5회 가능</p>
        <p className="whitespace-normal">Pick하고 싶은 친구가 없다면 셔플을 할 수 있습니다</p>
      </div>
      <Carousel className="bg-white">
        <CarouselContent>
          <CarouselItem>first</CarouselItem>
          <CarouselItem>second</CarouselItem>
          <CarouselItem>third</CarouselItem>
        </CarouselContent>
        <CarouselPrevious />
        <CarouselNext />
      </Carousel>
    </div>
  );
};

export default PickGuide;
