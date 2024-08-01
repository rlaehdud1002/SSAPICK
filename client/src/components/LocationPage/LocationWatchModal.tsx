import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTrigger,
} from 'components/ui/dialog';

interface LocationWatchModalProps {
  latitude: number | null;
  longitude: number | null;
}

const LocationWatchModal = ({
  latitude,
  longitude,
}: LocationWatchModalProps) => {
  return (
    <Dialog>
      <DialogTrigger>location Watch</DialogTrigger>
      <DialogContent className="border rounded-lg bg-[#E9F2FD] mx-2 w-4/5 relative">
        <DialogHeader>
          <DialogDescription className="flex justify-center">
            <span>latitude(위도) : {latitude}</span>
            <span>longitude(경도) : {longitude}</span>
          </DialogDescription>
        </DialogHeader>
      </DialogContent>
    </Dialog>
  );
};

export default LocationWatchModal;
