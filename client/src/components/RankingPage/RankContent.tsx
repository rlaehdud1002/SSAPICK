import TrophyIcon from 'icons/TrophyIcon';

interface RankContentProps {
  title: string;
  rankList: Array<{ rank: number; content: string }>;
}

const RankContent = ({ title, rankList }: RankContentProps) => {
  console.log(rankList);
  return (
    <div className='mb-10'>
      <div className="flex flex-row">
        <TrophyIcon width={25} height={25} />
        <h1 className="ms-2">{title}</h1>
      </div>
      {rankList.length !== 0 ? (
        rankList.map((rankItem) => {
          return (
            <div
              key={rankItem.rank}
              className="flex flex-row items-center border border-white rounded-lg m-3 p-2"
            >
              <span className="luckiest_guy text-color-5F86E9 mx-2 text-2xl">
                {rankItem.rank}
              </span>
              <span className="ms-2">{rankItem.content}</span>
            </div>
          );
        })
      ) : (
        <div className='text-center text-sm my-24'>랭킹을 위한 정보가 부족합니다.</div>
      )}
    </div>
  );
};

export default RankContent;
