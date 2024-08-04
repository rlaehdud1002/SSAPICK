import { accessTokenState } from "atoms/UserAtoms";
import { useEffect } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import { useRecoilState, useRecoilValue } from "recoil";

const AuthCallback = () => {
  const [searchParam] = useSearchParams();
  const [accessToken, setAccessToken] = useRecoilState(accessTokenState);
  const navigate = useNavigate()

  useEffect(() => {
    console.log("searchParam", searchParam);
    const accessToken = searchParam.get('accessToken');
    if (accessToken) {
      setAccessToken(accessToken);
      navigate('/mattermost');
      // {isAuth ? navigate('/home') : navigate('/mattermost')}
    }
  }, [])

  return <div></div>
}

export default AuthCallback;