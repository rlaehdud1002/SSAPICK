import React from 'react';

const KakaoButton = () => {
  const onClick = () => {
    const clientId = process.env.REACT_APP_API_KEY;
    const redirectUri = 'http://localhost:3000';
    
    window.location.href =
      `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${clientId}&redirect_uri=${redirectUri}`;
  };

  return (
    <form>
      <button
        type="button"
        onClick={onClick}
        className="flex my-2 items-center justify-center backGround-kakao w-72 h-14 rounded-md"
      >
        <img className="w-8 h-8 mr-4" src="icons/Kakao.png" alt="카카오로 시작하기" />
        카카오로 시작하기
      </button>
    </form>
  );
};

export default KakaoButton;
