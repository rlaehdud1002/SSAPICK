const RankingGuide = () => {
  return (
    <div className="flex flex-col items-center">
      <div className="flex items-center space-y-2 text-gray-700 text-center">
      <div className="flex min-w-[140px] flex-col items-center space-y-1 text-gray-700 text-center mx-2">
        <p className="whitespace-normal text-xs">카테고리 별 질문 <br/> 랭킹을 확인해보세요!</p>
        </div>
        <img width={200} height={400} src="icons/guide/Ranking.png" alt="" />
      </div>
    </div>
  );
};

export default RankingGuide;
