import { useQuery } from '@tanstack/react-query';
import { getRankList } from 'api/rankApi';
import { IRank, IRankList } from 'atoms/Rank.type';
import RankContent from 'components/RankingPage/RankContent';
import RankUser from 'components/RankingPage/RankUser';
import Loading from 'components/common/Loading';

const rankName = [
  'topMessageReceivers',
  'topMessageSenders',
  'topPickReceivers',
  'topPickSenders',
  'topReservePickcoUsers',
  'topSpendPickcoUsers',
] as const;

const Ranking = () => {
  const { data: rankList, isLoading } = useQuery<IRank>({
    queryKey: ['rank'],
    queryFn: async () => await getRankList(),
  });

  if (isLoading || !rankList) {
    return <Loading />;
  }

  return (
    <div className="m-6">
      <RankUser rankInfo={rankList['questionUserRanking']}/>
      {rankName.map((key) => {
        const rankItem: IRankList[] = rankList[key];
        return <RankContent key={key} title={key} rankInfo={rankItem} />;
      })}
    </div>
  );
};

export default Ranking;
