import RankContent from 'components/RankingPage/RankContent';
import { getRankList } from 'api/rankApi';
import { useQuery } from '@tanstack/react-query';
import { IRank, IRankList } from 'atoms/Rank.type';

const rankName = [
  'topMessageReceivers',
  'topMessageSenders',
  'topPickReceivers',
  'topPickSenders',
  'topSpendPickcoUsers',
] as const;

const Ranking = () => {
  const { data: rankList, isLoading } = useQuery<IRank>({
    queryKey: ['rank'],
    queryFn: getRankList,
  });

  console.log('rankList', rankList, isLoading);

  return (
    <div className="m-6">
      {rankList &&
        rankName.map((key) => {
          const rankItem: IRankList[] = rankList[key];
          return <RankContent key={key} title={key} rankInfo={rankItem} />;
        })}
    </div>
  );
};

export default Ranking;
