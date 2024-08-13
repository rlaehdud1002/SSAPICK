import { Link } from 'react-router-dom';

const Initial = () => {
  return (
    <div>
      <div className="text-center my-64">
        <span>상대방이 아직 당신을 지목하지 않았습니다.</span>
      </div>
      <div className="flex flex-col items-center">
        <Link to="/pick">
          <div className="bg-ssapick rounded-lg py-2 px-10 text-center my-3">
            내가 먼저 <b className="luckiest_guy">PICK</b>하러 가기!
          </div>
        </Link>
      </div>
    </div>
  );
};

export default Initial;
