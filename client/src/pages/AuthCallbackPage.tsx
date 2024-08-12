import { useQuery } from "@tanstack/react-query";
import { validCheck } from "api/validApi";
import { accessTokenState } from "atoms/UserAtoms";
import { IValid } from "atoms/Valid";
import { isValidateState, validState } from "atoms/ValidAtoms";
import { useEffect } from "react";
import { useLocation, useNavigate, useSearchParams } from "react-router-dom";
import { useRecoilState, useRecoilValue, useSetRecoilState } from "recoil";

const useAuthCallback = () => {
  const [searchParam] = useSearchParams();
  const [accessToken, setAccessToken] = useRecoilState(accessTokenState);
  const isValid = useRecoilValue(isValidateState);
  const setValidState = useSetRecoilState(validState);
  const navigate = useNavigate();
  const location = useLocation().pathname.split("/")[1];

  const initialize = async () => {
    const accessToken = searchParam.get("accessToken");
    if (accessToken) {
      setAccessToken(accessToken);
    }
  };

  useEffect(() => {
    initialize();
  }, []);

  return null;
};

const AuthCallback = () => {
  useAuthCallback();
  return <div></div>;
};

export default AuthCallback;
