import GoogleButton from "buttons/GoogleButton"
import KakaoButton from "buttons/KakaoButton"
import { useCookies } from "react-cookie"
import { useNavigate, useSearchParams } from "react-router-dom"
import LoginIcon from "../icons/LoginIcon"

const Login = () => {
    const cookies = useCookies()
    const nav = useNavigate()
    const [searchParam] = useSearchParams()

    console.dir(nav)
    console.dir(searchParam.get('accessToken'))

    if (cookies) {
        console.log(cookies)
    }

    return <div className="flex flex-col  items-center mt-36">
        <LoginIcon />
        <span className="luckiest_guy text-color-5F86E9 text-4xl mt-10 mb-28">SSAPICK</span>
        <KakaoButton />
        <GoogleButton />
    </div>
}

export default Login