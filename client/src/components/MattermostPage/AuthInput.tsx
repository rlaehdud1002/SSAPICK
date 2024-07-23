import { UseFormRegisterReturn } from "react-hook-form"

interface AuthInputProps {
    title: string
    register: UseFormRegisterReturn
    placeholder?: string
}

const AuthInput = ({title,placeholder,register}:AuthInputProps) => {
    return (
        <div className="flex justify-center w-96 py-2 my-2 border border-black rounded-md">
            <label className="relative" htmlFor={title}>
                {title}
                <span className="absolute -right-2 -top-1 text-red-600">*</span>
            </label>
            <input className="bg-transparent outline-none ml-5" type="text" {...register} placeholder={placeholder}/>
        </div>
    )
}

export default AuthInput
