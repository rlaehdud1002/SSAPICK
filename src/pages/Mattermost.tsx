import MattermostIcon from '../icons/MattermostIcon'
import AuthInput from '../components/MattermostPage/AuthInput'
import {useForm} from 'react-hook-form'
import DoneButton from 'buttons/DoneButton'

interface AuthFormm{
    id : string
    password : string
}

const Mattermost = () => {
    const { register, handleSubmit } = useForm<AuthFormm>();

    const onSubmit = (data: AuthFormm) => {
        console.log(data)
    }

    const onInvalid = (errors: any) => {
        console.log(errors)
    }
    return (
        <form onSubmit={handleSubmit(onSubmit, onInvalid)}>
        <div className="flex flex-col items-center mt-16">
        <h1 className='my-5' >Mattermost 인증</h1>
        <MattermostIcon/>
        <span className='text-xs mt-2 mb-20'>본 인증은 <span className='luckiest_guy'>ssapick</span> 서비스 이용을 위한 필수 사항입니다. </span>
        <AuthInput title="매터모스트 아이디" register={register("id",{
            required: "매터모스트 아이디를 입력해주세요."
        })}/>
        <AuthInput title="매터모스트 비밀번호" register={register("password",{
            required: "매터모스트 비밀번호를 입력해주세요."
        })}/>
        <span className='text-xs mb-10'>입력하신 인증정보는 오직 인증을 목적으로만 사용됩니다.</span>
        <DoneButton title="인증하기"/>
        </div>
        </form>
    )
}

export default Mattermost