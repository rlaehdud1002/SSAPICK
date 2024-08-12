import { IRankList } from "atoms/Rank.type";
import TrophyIcon from "icons/TrophyIcon";

interface RankContentProps {
  title: string;
  rankInfo: IRankList[];
}

interface RankTitle {
  [key: string]: string;
}

const rankName: RankTitle = {
  topMessageReceivers: "가장 많은 쪽지를 받은 사람!",
  topMessageSenders: "가장 많이 쪽지를 보낸 사람!",
  topPickReceivers: "가장 많이 PICK 받은 사람!",
  topPickSenders: "가장 많이 PICK한 사람!",
  topReservePickcoUsers: "가장 많은 PICKCO를 보유한 사람!",
  topSpendPickcoUsers: "가장 많은 PICKCO를 사용한 사람!",
};

const formatNum = (num: number): string => {
  return num.toLocaleString();
};

const RankContent = ({ title, rankInfo }: RankContentProps) => {
  const rankColor = ["text-[#D5A11E]", "text-[#A3A3A3]", "text-[#CD7F32]"];
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
              className="flex flex-row items-center justify-between border border-white rounded-lg m-3 p-2"
            >
              <div>
                <span className={`luckiest_guy ${rankColor[index]} mx-2 text-2xl pt-2`}>
                  {++index}
                </span>
                <span className="ms-2">{rank.user.name} </span>
                <span className="text-xs text-[#5f86e9] ml-1">
                  {rank.user.campusName} {rank.user.cohort}기 {rank.user.section}반
                </span>
              </div>
              <span className="luckiest_guy mr-4 text-[#5f86e9] text-xl">
                {formatNum(rank.count)}
              </span>
            </div>
          );
        })
      ) : (
        <div className="text-center text-sm my-24">랭킹을 위한 정보가 부족합니다.</div>
      )}
    </div>
  );
};

export default RankContent;
