import { IUserRanking } from 'atoms/Rank.type';
import RankUserContent from 'components/RankingPage/RankUserContent';
import TrophyIcon from 'icons/TrophyIcon';

interface RankUserProps {
  rankInfo: IUserRanking[];
}
const RankUser = ({ rankInfo }: RankUserProps) => {
  const rankLength = rankInfo.length;
  const rankIdx: Array<number> = [];

  while (rankIdx.length < 3) {
    const randomNum = Math.floor(Math.random() * rankLength);
    if (!rankIdx.includes(randomNum) && rankInfo[randomNum].count > 0) {
      rankIdx.push(randomNum);
    }
  }

  return (
    <div className="mb-10">
      <div className="flex flex-row items-center">
        <TrophyIcon width={25} height={25} />
        <span className="mx-2">
          질문별 <span className="luckiest_guy">SSAPICK</span> 1위!
        </span>
        <TrophyIcon width={25} height={25} />
      </div>
      {rankInfo.length >= 3 ? rankIdx.map((rankIndex) => {
        const rank: IUserRanking = rankInfo[rankIndex];
        return <RankUserContent key={rankIndex} rank={rank} />;
      }) : <div className="text-center text-sm my-24">
      랭킹을 위한 정보가 부족합니다.
    </div>}
    </div>
  );
};

export default RankUser;
