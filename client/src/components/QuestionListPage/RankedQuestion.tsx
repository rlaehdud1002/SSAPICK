import { useQuery } from "@tanstack/react-query";
import { getReceivePick } from "api/pickApi";
import { IPick } from "atoms/Pick.type";
import { spawn } from "child_process";
import TrophyIcon from "icons/TrophyIcon"


interface RankedQuestionProps {
    title: string;
    rankList: Array<{ rank: number; content: string }>;
}

const RankedQuestion = ({title, rankList}:RankedQuestionProps) => {
  const { data: picks, isLoading } = useQuery<IPick[]>({
    queryKey: ['pick', 'receive'],
    queryFn: getReceivePick,
  });

  console.log('picks', picks);
    return(
    <div className='mb-10'>
      <div className="flex flex-row">
        <TrophyIcon width={25} height={25} />
        <h1 className="ms-2">{title} <span className="luckiest_guy text-orange-400 text-lg">top 3</span></h1>
      </div>


      {/* {picks !== undefined && picks.length !== 0 ? (picks) : 
      (
        <div className='text-center text-sm my-24'>랭킹을 위한 정보가 부족합니다.</div>
      )} */}
      {picks !== undefined && picks.length !== 0 ? (
        rankList.map((rankItem) => {
          return (
            <div
              key={rankItem.rank}
              className="flex flex-row items-center border border-white rounded-lg m-3 p-2"
            >
              <span className="luckiest_guy text-color-5F86E9 mx-2 text-2xl pt-2">
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
    )
}

export default RankedQuestion