import InfoInput from "components/InfoInput"
import InfoSelect from "components/InfoSelect"
import {useForm} from "react-hook-form"

interface UserForm{
    name: string;
    gender: string;
    th: string;
    campus: string;
    class: string;
}


const UserInfo = () =>{
    const { register, handleSubmit } = useForm<UserForm>();

    const onSubmit = (data: UserForm) => {
        console.log(data)
    }

    const onInvalid = (errors: any) => {
        console.log(errors)
    }

    return <form onSubmit={handleSubmit(onSubmit, onInvalid)}>
        
        <div className="flex w-full flex-col justify-center items-center mx-10 space-y-2">

            <InfoInput title="이름" register={register("name", {
                required: "이름을 입력해주세요.",
                maxLength: { value: 10, message: "10글자로 입력해주세요." },
                
            })} />
            
            <InfoSelect title="성별"/>

            <InfoSelect title="기수"/>

            <InfoSelect title="캠퍼스"/>
            
            <InfoSelect title="반"/>
            
        </div>
    </form>
}
export default UserInfo