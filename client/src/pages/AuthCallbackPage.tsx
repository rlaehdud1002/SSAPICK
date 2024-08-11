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

  const handleValidityCheck = async () => {
    try {
      const data = await validCheck();
      setValidState(data);
      return data;
    } catch (error) {
      console.error("유효성 검사 실패", error);
      navigate("/");
      throw error;
    }
  };

  const handleRedirection = (data: IValid) => {
    console.log("유효성 검사33", data.lockedUser, data.mattermostConfirmed, data.validInfo);
    if (data.lockedUser) {
      navigate("/");
    } else if (!data.mattermostConfirmed && !location.includes("mattermost")) {
      navigate("/mattermost");
    } else if (!data.validInfo && !location.includes("infoinsert")) {
      navigate("/infoinsert");
    } else if (data.lockedUser === false && data.mattermostConfirmed && data.validInfo) {
      if (
        location.includes("auth") ||
        location.includes("infoinsert") ||
        location.includes("mattermost")
      ) {
        navigate("/home");
      }
    }
  };

  const initialize = async () => {
    const accessToken = searchParam.get("accessToken");
    if (accessToken) {
      setAccessToken(accessToken);
      try {
        const data = await handleValidityCheck();
        handleRedirection(data);
      } catch (error) {
        navigate("/");
      }
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
