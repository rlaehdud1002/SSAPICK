import { ErrorMessage } from '@hookform/error-message';
import { DialogDescription } from 'components/ui/dialog';
import { Input } from 'components/ui/input';
import { UseFormRegisterReturn } from 'react-hook-form';
import UserIcon from 'icons/UserIcon';

interface MessageInputModalProps {
  register: UseFormRegisterReturn;
  errors: object;
}

const MessageInputModal = ({ register, errors }: MessageInputModalProps) => {
  return (
    <DialogDescription className="flex flex-col justify-center">
      <div className="my-3 flex flex-row">
        <UserIcon />
        <h3 className="ms-3">11기 2반</h3>
      </div>
      <Input
        type="text"
        className="input-box border-none h-20 focus:outline-none"
        register={register}
      />
      <ErrorMessage
        errors={errors}
        name="message"
        render={({ message }) => (
          <h6 className="text-red-400 text-left text-xs my-1 fixed bottom-[50px]">
            {message}
          </h6>
        )}
      />
    </DialogDescription>
  );
};

export default MessageInputModal;
