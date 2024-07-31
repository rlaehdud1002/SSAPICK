import { accessTokenState } from "atoms/userAtoms";
import { useEffect } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import { useRecoilState } from "recoil";

const AuthCallback = () => {
  const [searchParam] = useSearchParams();
  const [accessToken, setAccessToken] = useRecoilState(accessTokenState);
  const navigate = useNavigate()

  useEffect(() => {
    const accessToken = searchParam.get('accessToken');
    if (accessToken) {
      setAccessToken(accessToken);
      navigate('/home');
    }
  }, [searchParam])

  return <div></div>
}

export default AuthCallback;