import { useQuery } from '@tanstack/react-query';
import BlockQuestionContent from './BlockQuestionContent';
import { IBlock, IBlockQuestion } from 'atoms/Block.type';
import { getBlockedQuestionList } from 'api/blockApi';
import Loading from 'components/common/Loading';

const BlockQuestion = () => {
  const { data: blockQuestion, isLoading } = useQuery<IBlockQuestion[]>({
    queryKey: ['blockQuestion'],
    queryFn: async () => await getBlockedQuestionList(),
  });

  if (isLoading) {
    return <Loading />;
  }

  return (
    <div className="mb-20">
      {blockQuestion?.length ? (
        blockQuestion.map((block, index) => (
          <BlockQuestionContent
            key={index}
            questionId={block.id}
            question={block}
          />
        ))
      ) : (
        <div className="flex justify-center">차단된 질문이 없습니다.</div>
      )}
    </div>
  );
};

export default BlockQuestion;
