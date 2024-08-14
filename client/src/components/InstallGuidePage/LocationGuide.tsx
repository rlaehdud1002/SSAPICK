const LocationGuide = () => {
  return (
    <div className="flex flex-col items-center justify-center mb-24">
      <div className="my-16 flex flex-col">
        <span className="luckiest_guy text-6xl">
          LOCATION SETTING GUIDE - IOS
        </span>
        <span className="text-center">
          <span className="luckiest_guy">Android</span>는 따로 설정하지 않아도
          됩니다.
        </span>
      </div>

      <div className="flex flex-row items-center">
        <div className="flex flex-col">
          <img
            src="images/splash/guide6.png"
            alt="noimage"
            width={200}
            className="rounded-2xl mx-5"
          />
          <span className="text-center my-3 text-white">
            설정 - 개인정보 보호 및 보안 클릭!
          </span>
        </div>
        <div className="flex flex-col">
          <img
            src="images/splash/guide7.png"
            alt="noimage"
            width={200}
            className="rounded-2xl mx-5"
          />
          <span className="text-center my-3 text-white">위치서비스 클릭!</span>
        </div>
        <div className="flex flex-col">
          <img
            src="images/splash/guide8.png"
            alt="noimage"
            width={200}
            className="rounded-2xl mx-5"
          />
          <span className="text-center my-3 text-white">
            Safari 웹 사이트 클릭!
          </span>
        </div>
        <div className="flex flex-col">
          <img
            src="images/splash/guide9.png"
            alt="noimage"
            width={200}
            className="rounded-2xl mx-5"
          />
          <span className="text-center my-3 text-white">
            앱을 사용하는 동안 클릭!
          </span>
        </div>
      </div>
    </div>
  );
};

export default LocationGuide;
