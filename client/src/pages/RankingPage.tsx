import RankContent from 'components/RankingPage/RankContent';

const Ranking = () => {
  const fakeList = [
    {
      rank: 1,
      content: '질문 1',
    },
    {
      rank: 2,
      content: '질문 2',
    },
    {
      rank: 3,
      content: '질문 3',
    },
  ];

  return (
    <div className="m-6">
      <RankContent title="가장 많이 대답한 질문?" rankList={fakeList} />
      <RankContent title="가장 많이 대답한 질문?" rankList={[]} />
    </div>
  );
};

export default Ranking;
