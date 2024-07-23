import TrophyIcon from "../icons/TrophyIcon";

interface RankingProps {
  isRanked: boolean;
}

const Ranking = ({isRanked}:RankingProps) => {
  return (
      isRanked ? 
    <div>

      <h1>Ranking Page</h1> 
      <div className="flex">
        <TrophyIcon width={25} height={25} />
        <h2>가장 많이 Pick한 질문</h2>
      </div>
      <div  >1.</div>
      <div  >2.</div>
      <div  >3.</div>
      </div>
      :
      <div> 랭킹을 위한 정보가 부족합니다.</div>

    
  );
};

export default Ranking;
