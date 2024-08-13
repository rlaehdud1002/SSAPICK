import { IUserRanking } from 'atoms/Rank.type';

interface RankUserContentProps {
  rank: IUserRanking;
}

const RankUserContent = ({ rank }: RankUserContentProps) => {
  return (
    <div className="border border-white   rounded-lg m-3 p-2 text-center flex flex-col py-4">
      <span className=''>{rank.questionContent}</span>
      <div className='my-2'>
        <span className="luckiest_guy text-xl text-[#5F86E9]">{rank.count} </span>
        <span className="luckiest_guy text-sm text-[#5F86E9]">PICK!</span>
      </div>
      <div className="flex flex-row items-center justify-center">
        <img src={rank.profileImage} alt="profile_image" className='rounded-full w-7 h-7' />
        <span className='mx-2'>{rank.name}</span>
        <span className="text-xs mr-2 text-[#5F86E9]">
          <span>{rank.campusName}</span>
          <span>{rank.cohort}기 </span>
          <span>{rank.section}반{' '}</span>
        </span>
      </div>
    </div>
  );
};

export default RankUserContent;
