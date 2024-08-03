import { IRankList } from 'atoms/Rank.type';
import TrophyIcon from 'icons/TrophyIcon';

interface RankContentProps {
  title: string;
  rankInfo: IRankList[];
}

interface RankTitle {
  [key: string]: string;
}

const rankName: RankTitle = {
  topMessageReceivers: '가장 많은 쪽지를 받은 사람!',
  topMessageSenders: '가장 많이 쪽지를 보낸 사람!',
  topPickReceivers: '가장 많이 PICK 받은 사람!',
  topPickSenders: '가장 많이 PICK한 사람!',
  topSpendPickcoUsers: '가장 많은 PICKCO를 사용한 사람!',
};

const RankContent = ({ title, rankInfo }: RankContentProps) => {
  let rankNum = 1;
  return (
    <div className="mb-10">
      <div className="flex flex-row">
        <TrophyIcon width={25} height={25} />
        <h1 className="ms-2">{rankName[title]}</h1>
      </div>
      {rankInfo.length >= 3 ? (
        rankInfo.map((rank, index) => {
          return (
            <div
              key={index}
              className="flex flex-row items-center border border-white rounded-lg m-3 p-2"
            >
              <span className="luckiest_guy text-color-5F86E9 mx-2 text-2xl pt-2">
                {rankNum++}
              </span>
              <span className="ms-2">
                {rank.user.campusName}캠퍼스 {rank.user.cohort}기{' '}
                {rank.user.section}반 {rank.user.name}
              </span>
            </div>
          );
        })
      ) : (
        <div className="text-center text-sm my-24">
          랭킹을 위한 정보가 부족합니다.
        </div>
      )}
    </div>
  );
};

export default RankContent;
