import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogFooter,
} from "components/ui/dialog";

interface MMfailModalProps {
  open: boolean;
  setOpen: (open: boolean) => void;
}

const MMfailModal = ({ open, setOpen }: MMfailModalProps) => {
  return (
    <Dialog open={open} onOpenChange={setOpen}>
      {open && (
        <DialogContent className="border rounded-lg bg-white/95 mx-2 w-4/5">
          <DialogHeader>
            <DialogTitle className="flex justify-start text-red-600">인증 실패</DialogTitle>
            <DialogDescription className="flex justify-center my-10 items-center text-color-000855">
              <h3 className="text-center">mm인증에 실패하였습니다. 다시 시도해 주세요.</h3>
            </DialogDescription>
          </DialogHeader>
          <DialogFooter className="flex flex-row justify-end">
            <button
              className="bg-red-600 text-white px-4 py-2 rounded"
              onClick={() => setOpen(false)}
            >
              닫기
            </button>
          </DialogFooter>
        </DialogContent>
      )}
    </Dialog>
  );
};

export default MMfailModal;
