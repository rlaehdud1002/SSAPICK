import { accessTokenState } from 'atoms/UserAtoms';
import { useRecoilValue } from 'recoil';
import Response from 'components/MainPage/Response';

import { useQuery } from '@tanstack/react-query';
import { getReceivePick } from 'api/pickApi';
import { IPick } from 'atoms/Pick.type';
import Initial from 'components/MainPage/Initial';

const Home = () => {
  const accessToken = useRecoilValue(accessTokenState);
  console.log(accessToken);

  const { data: picks, isLoading } = useQuery<IPick[]>({
    queryKey: ['pick', 'receive'],
    queryFn: getReceivePick,
  });

  return (
    <div className="m-6">
      {picks && <Response picks={picks} isLoading={isLoading} />}
      {!picks && <Initial />}
    </div>
  );
};

export default Home;
