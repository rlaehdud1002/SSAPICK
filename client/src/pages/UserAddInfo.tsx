import DoneButton from "buttons/DoneButton";
import InfoInput from "components/LoginPage/InfoInput";

import { useForm } from "react-hook-form";

interface AddUserForm {
    mbti: string;
    major: string;
    birth: Date;
    town: string;
    hobby: string;
}

const UserAddInfo = () => {
    const { register, handleSubmit } = useForm<AddUserForm>();

    const onSubmit = (data: AddUserForm) => {
        console.log(data)
    }

    const onInvalid = (errors: any) => {
        console.log(errors)
    }

    return <form onSubmit={handleSubmit(onSubmit, onInvalid)}>
        <div className="flex flex-col my-5">
        <h3 style={{ fontSize: 13 }}>추가 정보 입력</h3>
        <div style={{ fontSize: 13 }}>추후 힌트로 사용됩니다.</div>
        <div className="mb-20" style={{ color: "red", fontSize: 10 }}>최소 2개의 정보 입력이 필수입니다.</div>
        <div className="flex w-full flex-col justify-center items-center space-y-2">

            <InfoInput title="MBTI" register={register("mbti", {
                required: "MBTI를 입력해주세요.",
                maxLength: { value: 4, message: "4글자로 입력해주세요." },
                minLength: { value: 4, message: "4글자로 입력해주세요." },
            })} />
            <InfoInput title="전공" register={register("major", {
                required: "전공을 입력해주세요.",
                maxLength: { value: 20, message: "20글자 이하로 입력해주세요." }
            })} />
            <InfoInput title="생년월일" register={register("birth", {
                required: "생년월일을 입력해주세요."
            })} />
            <InfoInput title="동네 " register={register("town", {
                required: "동네를 입력해주세요.",
                maxLength: { value: 20, message: "20글자 이하로 입력해주세요." }
            })} />
            <InfoInput title="관심사" register={register("hobby", {
                required: "관심사를 입력해주세요.",
                maxLength: { value: 20, message: "20글자 이하로 입력해주세요." }
            })} />
            <div>
            <DoneButton title="완료" />
            </div>
            </div>
        </div>
    </form>


}

export default UserAddInfo