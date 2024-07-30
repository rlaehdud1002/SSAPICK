import { ErrorMessage } from '@hookform/error-message';
import { DialogDescription } from '@radix-ui/react-dialog';
import { Input } from 'components/ui/input';
import { UseFormRegisterReturn } from 'react-hook-form';

interface QuestionInputProps {
  register: UseFormRegisterReturn;
  errors: object;
}

const QuestionInput = ({ register, errors }: QuestionInputProps) => {
  return (
    <DialogDescription className="flex flex-col justify-center">
      <Input
        type="text"
        className="input-box border-none w-full h-20 focus:outline-none mt-1 mb-2"
        register={register}
      />
      <ErrorMessage
        errors={errors}
        name="newQuestion"
        render={({ message }) => (
          <h6 className="text-red-400 text-left text-xs">{message}</h6>
        )}
      />
    </DialogDescription>
  );
};

export default QuestionInput;
