// import { GOOGLE_AUTH_URL } from "api/clientApi";

const GoogleButton = () => {
  const onClick = () => {
    window.location.href = `https://www.ssapick.kro.kr/oauth2/authorization/google`;
  };

  return (
    <form>
      <button
        onClick={onClick}
        className=" flex my-2 items-center justify-center background-google w-72 h-14 rounded-lg"
      >
        <img className="w-7 h-7 mr-7" src="icons/Google.png" alt="google" />
        구글로 시작하기
      </button>
    </form>
  );
};

export default GoogleButton;
