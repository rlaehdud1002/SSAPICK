import { accessTokenState } from "atoms/userAtoms";
import Response from "components/MainPage/Response";
import { useRecoilValue } from "recoil";


const Home = () => {
  const accessToken = useRecoilValue(accessTokenState);
  console.log(accessToken);
  return (
    <div className="m-6">
      <Response />
    </div>
  );
};

export default Home;
