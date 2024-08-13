const FriendGuide = () => {
  return (
    <div className="flex flex-col items-center my-10 p-6 bg-gray-100/50 rounded-lg shadow-lg max-w-full min-w-full px-4">
      <h1 className="text-3xl font-bold text-[#5f86e9] mb-5 text-center">Follow 가이드</h1>
      <div className="flex flex-col items-center my-3 space-y-2 text-gray-700 text-center">
        <p className="whitespace-normal">내 친구 목록을 볼수있습니다! </p>
        <p className="whitespace-normal">(같은 반은 기본적으로 친구 관계입니다!)</p>
      </div>
      <div className="flex justify-center">
        <img width={200} src="icons/guide/FriendsList.png" alt="Pick" />
      </div>

      <div className="flex flex-col items-center mt-10 mb-5 space-y-2 text-gray-700 text-center">
        <p className="whitespace-normal text-xl font-bold text-[#5f86e9]">추천 친구 / 친구 검색</p>
      </div>

      <div className="flex justify-center mb-5">
        <img width={200} src="icons/guide/searchFriends.png" alt="Pick" />
      </div>
    </div>
  );
};

export default FriendGuide;
