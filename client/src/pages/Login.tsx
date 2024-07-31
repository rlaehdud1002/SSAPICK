import KakaoButton from "buttons/KakaoButton"
import LoginIcon from "../icons/LoginIcon"
import GoogleButton from "buttons/GoogleButton"
import axios from "axios"
interface kakaoResponse {
    access_token: string
}

interface googleResponse {
    access_token: string
}

const Login = () =>{
    
    

    return <div className="flex flex-col  items-center mt-36">
        <LoginIcon/> 
        <span className="luckiest_guy text-color-5F86E9 text-4xl mt-10 mb-28">SSAPICK</span>
        <KakaoButton/>
        <GoogleButton/>
    </div>
}

export default Login