const LocationGuide = () => {
  return (
    <div className="flex flex-col items-center justify-center mb-24">
      <span className="luckiest_guy text-color-000855 text-6xl my-16">
        LOCATION SETTING GUIDE
      </span>
      <div className="flex flex-row items-center">
        <div className="flex flex-col">
          <img
            src="images\splash\guide6.png"
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
            src="images\splash\guide7.png"
            alt="noimage"
            width={200}
            className="rounded-2xl mx-5"
          />
          <span className="text-center my-3 text-white">위치서비스 클릭!</span>
        </div>
        <div className="flex flex-col">
          <img
            src="images\splash\guide8.png"
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
            src="images\splash\guide9.png"
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
