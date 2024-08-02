// import { KAKAO_AUTH_URL } from "api/clientApi";

const KakaoButton = () => {
  const onKakao = () => {
    window.location.href = `https://www.ssapick.kro.kr/oauth2/authorization/kakao`;
  };

  return (
    <form>
      <button
        type="button"
        onClick={onKakao}
        className="flex my-2 items-center justify-center backGround-kakao w-72 h-14 rounded-lg">
        <img className="w-8 h-8 mr-4" src="icons/Kakao.png" alt="카카오로 시작하기" />
        카카오로 시작하기
      </button>
    </form>
  );
};

export default KakaoButton;
