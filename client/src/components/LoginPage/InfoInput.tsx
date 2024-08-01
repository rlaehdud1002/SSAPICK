import { ErrorMessage } from '@hookform/error-message';
import { UseFormRegisterReturn } from "react-hook-form";


interface InfoInputProps {
  name: string,
  title: string,
  register: UseFormRegisterReturn,
  errors: object
  value?: string
}

const InfoInput = ({ name, title, register, errors,value }: InfoInputProps) => {
  console.log(name)
  return (
    <div className='flex flex-col'>
      <div>
      <div className="w-72 h-10 text-sm flex justify-center py-2 border border-black rounded-lg">
      <label className="relative w-40 ml-8" htmlFor={name}>
        {title}
      </label>
      <input
        type="text"
        id={name}
        autoComplete="off"
        value={value}
        className="bg-transparent outline-none ml-10"
        {...register}
      />
      </div>
      <div className='my-1 ml-2'>
      <ErrorMessage
        errors={errors}
        name={name}
        render={({ message }) => (
          <h6 className="text-red-400 text-xs ">
            {message}
          </h6>
        )} />
        </div>
      </div>
    </div>

  );
}

export default InfoInput;