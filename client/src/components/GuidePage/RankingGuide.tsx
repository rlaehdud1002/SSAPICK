const RankingGuide = () => {
  return (
    <div className="flex flex-col items-center my-10 p-6 min-w-full bg-gray-100/50 rounded-lg shadow-lg max-w-full px-4">
      <h1 className="text-3xl font-bold text-[#5f86e9] my-5 text-center">랭킹</h1>
      <div className="flex items-center my-3 space-y-2 text-gray-700 text-center">
        <p className="whitespace-normal my-5">카테고리 별 질문 랭킹을 확인해보세요!</p>
        <img width={200} height={400} src="icons/guide/Ranking.png" alt="" />
      </div>
    </div>
  );
};

export default RankingGuide;
