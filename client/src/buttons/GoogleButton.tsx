// import { GOOGLE_AUTH_URL } from "api/clientApi";

import { GOOGLE_AUTH_URL } from "api/clientApi";
import { Link } from "react-router-dom";

const GoogleButton = () => {
  return (
      <button
        onClick={() => {
          window.location.href = GOOGLE_AUTH_URL;
        }}
        className=" flex my-2 items-center justify-center background-google w-72 h-14 rounded-lg"
      >
        <img className="w-7 h-7 mr-7" src="icons/Google.png" alt="google" />
        구글로 시작하기
      </button>
  );
};

export default GoogleButton;
