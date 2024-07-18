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
      <div className="w-52 h-8 border-2 border-white rounded-md " >1. {}</div>
      <div className="w-52 h-8 border-2 border-white rounded-md " >2. {}</div>
      <div className="w-52 h-8 border-2 border-white rounded-md" >3. {}</div>
      <div className="flex">
        <TrophyIcon width={25} height={25} />
        <h2>가장 많이 스킵된 질문</h2>
      </div>
      <div className="w-52 h-8 border-2 border-white rounded-md " >1. {}</div>
      <div className="w-52 h-8 border-2 border-white rounded-md " >2. {}</div>
      <div className="w-52 h-8 border-2 border-white rounded-md " >3. {}</div>
      <div className="flex">
        <TrophyIcon width={25} height={25} />
        <h2>가장 많이 지목된 사람</h2>
      </div>
      <div className="w-52 h-8 border-2 border-white rounded-md " >1. {}</div>
      <div className="w-52 h-8 border-2 border-white rounded-md " >2. {}</div>
      <div className="w-52 h-8 border-2 border-white rounded-md " >3. {}</div>
      </div>
      :
      <div> 랭킹을 위한 정보가 부족합니다.</div>

    
  );
};

export default Ranking;
