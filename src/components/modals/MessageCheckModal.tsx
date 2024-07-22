import { DialogDescription } from '@radix-ui/react-dialog';

const MessageCheckModal = () => {
  return (
    <DialogDescription className="flex justify-center">
      <h1 className="my-12">전송이 완료되었습니다.</h1>
    </DialogDescription>
  );
};

export default MessageCheckModal;