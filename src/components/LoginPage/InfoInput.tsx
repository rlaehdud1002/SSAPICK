import { UseFormRegisterReturn } from "react-hook-form";

interface InfoInputProps {
  title: string,
  register: UseFormRegisterReturn
}

const InfoInput = ({ title, register }: InfoInputProps) => {
  return (
    
    <div className="w-72 h-10 text-sm flex justify-center py-2 border border-black rounded-md">
      <label className="relative" htmlFor={title}>
        {title}
        <span className="absolute -right-2 -top-1 text-red-600">*</span>
      </label>
      <input type="text" autoComplete="off" className="bg-transparent outline-none ml-10" {...register} />
    </div>
   
  );
}

export default InfoInput;