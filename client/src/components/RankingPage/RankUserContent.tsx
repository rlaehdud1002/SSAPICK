import { IUserRanking } from 'atoms/Rank.type';

interface RankUserContentProps {
  rank: IUserRanking;
}

const RankUserContent = ({ rank }: RankUserContentProps) => {
  return (
    <div className="border border-white rounded-lg m-3 p-2 text-center flex flex-col">
      <span>{rank.questionContent}</span>
      <div className="flex flex-row items-center justify-center my-2">
        <span className="text-sm mr-2 text-[#5F86E9] ">
          {rank.campusName} {rank.cohort}기 {rank.section}반{' '}
        </span>
        <span>{rank.name}</span>
      </div>
    </div>
  );
};

export default RankUserContent;
