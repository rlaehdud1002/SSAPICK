import Response from 'components/MainPage/Response';
import Initial from 'components/MainPage/Initial';

import { useQuery } from '@tanstack/react-query';
import { getReceivePick } from 'api/pickApi';
import { IPick } from 'atoms/Pick.type';

const Home = () => {
  const { data: picks, isLoading } = useQuery<IPick[]>({
    queryKey: ['pick', 'receive'],
    queryFn: getReceivePick,
  });

  console.log('picks', picks);

  return (
    <div className="m-6">
      {picks !== undefined && picks.length !== 0 ? (
        <Response picks={picks} isLoading={isLoading} />
      ) : (
        <Initial />
      )}
    </div>
  );
};

export default Home;
