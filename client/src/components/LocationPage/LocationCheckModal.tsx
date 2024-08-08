import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTrigger,
} from 'components/ui/dialog';

interface LocationCheckModalProps {
  latitude: number | null;
  longitude: number | null;
}

const LocationCheckModal = ({
  latitude,
  longitude,
}: LocationCheckModalProps) => {
  return (
    <Dialog>
      <DialogTrigger>location check</DialogTrigger>
      <DialogTitle></DialogTitle>
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

export default LocationCheckModal;
