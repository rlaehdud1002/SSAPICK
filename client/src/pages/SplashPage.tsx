import { useNavigate } from 'react-router-dom';

const Splash = () => {
  const navigate = useNavigate();
  return (
    <div className="flex flex-row h-screen items-center justify-center splash absolute w-screen">
      <div className="bg-white/50 rounded-xl w-80 h-100 mr-24">
        <img src="" alt="이미지" />
      </div>
      <div className="flex flex-col items-center justify-center">
        <div className="text-white text-4xl my-8">
          <span className="luckiest_guy">SSAFY</span>인의 모든 것,
        </div>
        <span className="luckiest_guy text-8xl text-color-5F86E9">SSAPICK</span>
        <div onClick={() => navigate('/install')}>
          <img
            src="images\splash\qrcode_www.ssapick.kro.kr.png"
            alt="없어!"
            width={150}
            height={150}
            className="rounded-2xl my-12 cursor-pointer"
          />
        </div>
        <span className="text-white">
          QR 코드를 찍고 <span className="luckiest_guy">SSAPICK</span>을
          다운받아보세요!
        </span>
        <span className="text-color-000855 text-xs">
          QR 코드를 클릭하고 <span className="luckiest_guy">INSTALL GUIDE</span>
          를 확인해보세요!
        </span>
      </div>
    </div>
  );
};

export default Splash;
