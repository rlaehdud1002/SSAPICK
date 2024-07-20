import { UseFormRegisterReturn } from "react-hook-form";

interface InfoInputProps {
  title: string,
  register: UseFormRegisterReturn
}

const InfoInput = ({ title, register }: InfoInputProps) => {
  return (
    
    <div className="w-18 flex justify-center space-x-2 py-2 px-3 border border-black rounded-md">
      <label className="relative" htmlFor={title}>
        {title}
        <span className="absolute -right-2 -top-1 text-red-600">*</span>
      </label>
      <input className="bg-transparent outline-none" type="text" {...register} />
    </div>
   
  );
}

export default InfoInput;