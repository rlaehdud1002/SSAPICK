import { ErrorMessage } from "@hookform/error-message";
import { UseFormRegisterReturn } from "react-hook-form";
import { Textarea } from "components/ui/textarea";

interface InputModalProps {
  register: UseFormRegisterReturn;
  errors: object;
  name: string;
}

const InputModal = ({ register, errors, name }: InputModalProps) => {
  return (
    <div className="flex flex-col justify-center">
      <Textarea
        register={register}
      />
      <ErrorMessage
        errors={errors}
        name={name}
        render={({ message }) => (
          <h6 className="text-red-400 text-left text-xs fixed bottom-[50px]">{message}</h6>
        )}
      />
    </div>
  );
};

export default InputModal;
