import DoneButton from "buttons/DoneButton";
import InfoInput from "components/LoginPage/InfoInput";
import InfoSelect from "components/LoginPage/InfoSelect";
import { useNavigate } from "react-router-dom";

import { useForm } from "react-hook-form";

interface AddUserForm {
    mbti: string;
    major: string;
    birth: Date;
    town: string;
    hobby: string;
    class: number;
}

const UserAddInfo = () => {
    const navigate = useNavigate()

    const { register, handleSubmit, setValue, formState:{errors} } = useForm<AddUserForm>();

    const onSubmit = (data: AddUserForm) => {
        console.log(data)
        navigate('/home')
    }

    const onInvalid = (errors: any) => {
        console.log(errors)
    }

    return <form onSubmit={handleSubmit(onSubmit, onInvalid)}>
        <div className="flex flex-col my-5">
        <div className="ml-10">
        <h3 style={{ fontSize: 15 }}>추가 정보 입력</h3>
        <div style={{ fontSize: 11 }}>추후 랜덤 힌트로 사용됩니다.</div>
        <div className="mb-20" style={{ color: "red", fontSize: 10 }}>모든 정보 입력이 필수입니다.</div>
        </div>
        <div className="flex w-full flex-col justify-center items-center space-y-2">

            <InfoInput name="mbti" title="MBTI" register={register("mbti", {
                required: "MBTI를 입력해주세요.",
                maxLength: { value: 4, message: "4글자로 입력해주세요." },
                minLength: { value: 4, message: "4글자로 입력해주세요." },
                pattern: { value: /^[E,I,N,S,T,F,P,J]{4}$/, message: "대문자 MBTI 유형으로 입력해주세요." }
            })} errors={errors} />

            <InfoSelect name="class" title="반" register={register("class", {
                    required: "반을 선택해주세요."
                })} setValue={(value: number) => setValue("class", value)} errors={errors}/>


            <InfoInput name="major" title="전공" register={register("major", {
                required: "전공을 입력해주세요.",
                maxLength: { value: 20, message: "20글자 이하로 입력해주세요." }
            })}errors={errors} />
            <InfoInput name="birth" title="생년월일" register={register("birth", {
                required: "생년월일을 입력해주세요.",
                pattern:{value:/^\d{4}-\d{2}-\d{2}$/, message:"YYYY-MM-DD 형식으로 입력해주세요."
            }})}errors={errors} />
            <InfoInput name="town" title="동네 " register={register("town", {
                required: "동네를 입력해주세요.",
                maxLength: { value: 20, message: "20글자 이하로 입력해주세요." }
            })}errors={errors} />
            <InfoInput name="hobby" title="관심사" register={register("hobby", {
                required: "관심사를 입력해주세요.",
                maxLength: { value: 20, message: "20글자 이하로 입력해주세요." }
            })}errors={errors} />
            <div className="flex">
                <div className="bg-white w-3 h-3 rounded-full my-2 mx-1 opacity-50"></div>
                <div className="bg-white w-3 h-3 rounded-full my-2 mx-1 "></div>
                </div>
            <div>
            <DoneButton title="완료" />
            </div>
            </div>
        </div>
    </form>


}

export default UserAddInfo