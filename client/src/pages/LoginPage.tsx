import { refresh } from "api/authApi";
import { accessTokenState } from "atoms/UserAtoms";
import KakaoButton from "buttons/KakaoButton";
import MoveGuide from "components/GuidePage/MoveGuide";
import { useEffect } from "react";
import { useSetRecoilState } from "recoil";
import LoginIcon from "../icons/LoginIcon";

const Login = () => {
  const setAccessToken = useSetRecoilState(accessTokenState);

  useEffect(() => {
    refresh()
      .then((response) => {
        setAccessToken(response.accessToken);
      })
      .catch((error) => {
        console.error(error);
      });
  }, [setAccessToken]);

  return (
    <div className="flex flex-col  items-center mt-36">
      <LoginIcon />
      <span className="luckiest_guy text-color-5F86E9 text-4xl mt-10 mb-20">SSAPICK</span>
      <MoveGuide />
      <KakaoButton />
      {/* <GoogleButton /> */}
    </div>
  );
};

export default Login;
