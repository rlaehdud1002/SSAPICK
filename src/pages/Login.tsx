import KakaoButton from "buttons/KakaoButton"
import LoginIcon from "../icons/LoginIcon"
import GoogleButton from "buttons/GoogleButton"

const Login = () =>{
    return <div className="flex flex-col items-center align-middle">
        <LoginIcon/> 
        <KakaoButton/>
        <GoogleButton/>
    </div>
}

export default Login