import { ErrorMessage } from '@hookform/error-message';
import { UseFormRegisterReturn } from 'react-hook-form';
import UserMaskIcon from 'icons/UserMaskIcon';
import { Textarea } from 'components/ui/textarea';

interface InputModalProps {
  register: UseFormRegisterReturn;
  errors: object;
  title?: string;
  name: string;
}

const InputModal = ({ register, errors, title, name }: InputModalProps) => {
  return (
    <div className="flex flex-col justify-center">
      {title === 'messageSend' && (
        <div className="mb-3 flex flex-row">
          <UserMaskIcon gen="F" />
          <h3 className="ml-3">11기 2반</h3>
        </div>
      )}
      <Textarea
        className="input-box border-none h-20 focus:outline-none w-full"
        register={register}
      />
      <ErrorMessage
        errors={errors}
        name={name}
        render={({ message }) => (
          <h6 className="text-red-400 text-left text-xs fixed bottom-[50px]">
            {message}
          </h6>
        )}
      />
    </div>
  );
};

export default InputModal;
