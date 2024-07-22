import DoneButton from "buttons/DoneButton"
import InfoInput from "components/LoginPage/InfoInput"
import InfoSelect from "components/LoginPage/InfoSelect"
import ProfileCameraIcon from "icons/ProfileCameraIcon"
import { useState } from "react"
import { useForm } from "react-hook-form"


interface UserForm {
    name: string;
    gender: string;
    th: number;
    campus: string;
    class: number;
}


const UserInfo = () => {
    const { register, handleSubmit, setValue } = useForm<UserForm>();
    const [uploadImage, setUploadImage] = useState<File | undefined>(undefined)

    const onSubmit = (data: UserForm) => {
        const form = new FormData()
        form.append("name", data.name)
        form.append("image", "")
        console.log(data)
    }

    const onInvalid = (errors: any) => {
        console.log("error", errors)
    }

    return (

        <form onSubmit={handleSubmit(onSubmit, onInvalid)}>

            <div className="flex w-full flex-col justify-center items-center mt-10 space-y-2">
                <div className="mb-10">
                    <ProfileCameraIcon setUploadImage={setUploadImage} />
                </div>

                <InfoInput title="이름" register={register("name", {
                    required: "이름을 입력해주세요.",
                    maxLength: { value: 10, message: "10글자로 입력해주세요." },
                })} />

                <InfoSelect title="성별" register={register("gender", {
                    required: "성별을 선택해주세요."
                })} setValue={(value: string) => setValue("gender", value)} />

                <InfoSelect title="기수" register={register("th", {
                    required: "기수를 선택해주세요."
                })} setValue={(value: number) => setValue("th", value)} />

                <InfoSelect title="캠퍼스" register={register("campus", {
                    required: "캠퍼스를 선택해주세요."
                })} setValue={(value: string) => setValue("campus", value)} />

                <InfoSelect title="반" register={register("class", {
                    required: "반을 선택해주세요."
                })} setValue={(value: number) => setValue("class", value)} />

                <div>
                    <DoneButton title="완료" />
                </div>
            </div>
        </form>
    )
}
export default UserInfo