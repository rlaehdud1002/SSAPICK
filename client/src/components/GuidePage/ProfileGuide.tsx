const ProfileGuide = () => {
  return (
    <div className="flex flex-col items-center min-w-full max-w-full px-4">
      <div className="flex">
      <img width={200} height={400} src="icons/guide/Profile.png" alt="" />
        <div className="flex flex-col items-center space-y-1 text-gray-700 text-center justify-center mx-2">
          <p className="text-pretty text-xs">내 개인 정보 수정과</p>
          <p className="text-pretty text-xs">여러 싸픽 내 정보 확인</p>
          <p className="text-pretty text-xs pb-4">다양한 설정 관리까지!</p>

          <p className="text-pretty text-xs">마이 페이지에서</p>
          <p className="text-pretty text-xs">한번에 처리하세요!</p>
        </div>
        
      </div>
    </div>
  );
};
export default ProfileGuide;

