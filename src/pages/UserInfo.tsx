import InfoInput from "components/InfoInput"
import InfoDrop from "components/InfoDrop"
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
            
            <InfoDrop title="성별"/>

            <InfoInput title="기수" register={register("th", {
                required: "기수를 입력해주세요."
            })} />
            <InfoInput title="캠퍼스 " register={register("campus", {
                required: "캠퍼스를 입력해주세요.",
                maxLength: { value: 20, message: "20글자 이하로 입력해주세요." }
            })} />
            <InfoInput title="반" register={register("class", {
                required: "반을 입력해주세요.",
                maxLength: { value: 3, message: "3글자 이하로 입력해주세요." }
            })} />

            
        </div>
    </form>
}
export default UserInfo