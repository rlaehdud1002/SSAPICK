import { ErrorMessage } from '@hookform/error-message';
import { Input } from 'components/ui/input';
import { UseFormRegisterReturn } from 'react-hook-form';
import UserMaskIcon from 'icons/UserMaskIcon';

interface MessageInputModalProps {
  register: UseFormRegisterReturn;
  errors: object;
}

const MessageInputModal = ({ register, errors }: MessageInputModalProps) => {
  return (
    <div className="flex flex-col justify-center">
      <div className="mb-3 flex flex-row">
        <UserMaskIcon gen="female" checked />
        <h3 className="ml-3">11기 2반</h3>
      </div>
      <Input
        type="text"
        className="input-box border-none h-20 focus:outline-none w-full"
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
    </div>
  );
};

export default MessageInputModal;
