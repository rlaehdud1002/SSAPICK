const InstallAndroid = () => {
  return (
    <div className="flex flex-col items-center justify-center">
      <span className="luckiest_guy text-color-5F86E9 text-6xl my-16">
        INSTALL GUIDE - Android
      </span>
      <div className="flex flex-row items-center">
        <div className="flex flex-col">
          <img
            src="images/splash/android1.png"
            alt="noimage"
            width={200}
            className="rounded-2xl mx-5"
          />
          <span className="text-center my-3 text-white">설치 버튼 클릭!</span>
        </div>
        <span className="luckiest_guy text-white">OR</span>
        <div className="flex flex-col">
          <img
            src="images/splash/android2.png"
            alt="noimage"
            width={200}
            className="rounded-2xl mx-5"
          />
          <span className="text-center my-3 text-white">홈 화면에 추가 버튼 클릭!</span>
        </div>
        <div className="flex flex-col">
          <img
            src="images/splash/android3.png"
            alt="noimage"
            width={200}
            className="rounded-2xl mx-5"
          />
          <span className="text-center my-3 text-white">설치 완료!</span>
        </div>
      </div>
    </div>
  );
};

export default InstallAndroid;
