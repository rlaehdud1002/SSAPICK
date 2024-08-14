import { useQuery } from '@tanstack/react-query';
import BlockFriendContent from './BlockFriendContent';
import { getBlockedList } from 'api/blockApi';
import { IBlock } from 'atoms/Block.type';
import Loading from 'components/common/Loading';

const BlockFriend = () => {
  const { data: blocks, isLoading } = useQuery<IBlock[]>({
    queryKey: ['blocks'],
    queryFn: getBlockedList,
  });

  if (isLoading) {
    return <Loading />;
  }
  return (
    <div className="mb-20">
      {blocks?.length ? (
        blocks.map((block, index) => (
          <div>
            <BlockFriendContent
              key={index}
              cohort={block.cohort}
              userId={block.userId}
              campusName={block.campusName}
              campusSection={block.campusSection}
              name={block.nickname}
              profileImage={block.profileImage}
            />
          </div>
        ))
      ) : (
        <div className="flex justify-center">차단된 친구가 없습니다.</div>
      )}
    </div>
  );
};

export default BlockFriend;
