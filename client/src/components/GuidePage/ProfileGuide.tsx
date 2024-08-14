const ProfileGuide = () => {
  return (
    <div className="flex flex-col items-center my-10 p-6 min-w-full bg-gray-100/50 rounded-lg shadow-lg max-w-full px-4">
      <h1 className="text-3xl font-bold text-[#5f86e9] mb-5 text-center">마이페이지</h1>
      <div className="flex">
        <div className="flex flex-col items-center my-3 space-y-2 text-gray-700 text-center mt-20">
          <p className="whitespace-normal">내가 입력한 정보를 확인하고,</p>
          <p className="whitespace-normal mb-5">
            보유한 픽코의 개수, 지목받은 수, 팔로우 수까지 확인!
          </p>
          <p className="whitespace-normal mt-5">하단의 메뉴를 통해</p>
          <p className="whitespace-normal">다양한 페이지로 이동해보세요!</p>
        </div>
        <img width={200} src="icons/guide/Profile.png" alt="" />
      </div>
    </div>
  );
};
export default ProfileGuide;
