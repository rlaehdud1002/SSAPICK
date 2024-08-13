const Install = () => {
  return (
    <div className="flex flex-col items-center justify-center">
      <span className="luckiest_guy text-color-5F86E9 text-6xl my-16">
        INSTALL GUIDE
      </span>
      <div className="flex flex-row items-center">
        <div className="flex flex-col">
          <img
            src="images\splash\guide1.png"
            alt="noimage"
            width={200}
            className="rounded-2xl mx-5"
          />
          <span className="text-center my-3 text-white">공유 버튼 클릭!</span>
        </div>
        <div className="flex flex-col">
          <img
            src="images\splash\guide2.png"
            alt="noimage"
            width={200}
            className="rounded-2xl mx-5"
          />
          <span className="text-center my-3 text-white">
            홈 화면에 추가 클릭!
          </span>
        </div>
        <div className="flex flex-col">
          <img
            src="images\splash\guide3.png"
            alt="noimage"
            width={200}
            className="rounded-2xl mx-5"
          />
          <span className="text-center my-3 text-white">추가 버튼 클릭!</span>
        </div>
        <div className="flex flex-col">
          <img
            src="images\splash\guide4.png"
            alt="noimage"
            width={150}
            className="rounded-2xl mx-5"
          />
          <span className="text-center my-3 text-white">설치 완료!</span>
        </div>
      </div>
    </div>
  );
};

export default Install;
