import { Link } from 'react-router-dom';

const NotFoundPage = () => {
  return (
    <div className="relative h-screen">
      <div className="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 text-center flex flex-col items-center">
        <div>
          <span className="text-2xl">존재하지 않는 페이지입니다</span>
        </div>
        <Link to="/">
          <div className="bg-ssapick rounded-lg mt-6 mb-3 px-10 py-2">
            로그인 페이지로!
          </div>
        </Link>
        <Link to="/home">
          <div className="bg-white hover:bg-white/80 rounded-lg mb-4 px-10 py-2">
            메인 페이지로!
          </div>
        </Link>
      </div>
    </div>
  );
};

export default NotFoundPage;
