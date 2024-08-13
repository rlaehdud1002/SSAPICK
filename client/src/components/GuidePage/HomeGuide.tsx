import { Carousel, 
  CarouselContent, 
  CarouselItem, 
  CarouselNext, 
  CarouselPrevious 
} from "components/ui/carousel";

const HomeGuide = () => {
  return (
    <div>
      <span className="text-lg">메인 페이지</span>

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
  )
};

export default HomeGuide;
