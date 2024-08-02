import { UseFormRegisterReturn } from "react-hook-form"

interface AuthInputProps {
    title: string
    register: UseFormRegisterReturn
    placeholder?: string
    type?: string
}

const AuthInput = ({ title, placeholder, register, type }: AuthInputProps) => {
    return (
        <div className="flex justify-center w-96 py-2 my-2 border border-black rounded-lg">
            <label className="relative w-full ml-7" htmlFor={title}>
                {title}

            </label>
            <input
                className="bg-transparent outline-none px-5"
                autoComplete="off"
                type={type} {...register} placeholder={placeholder} />
        </div>
    )
}

export default AuthInput
