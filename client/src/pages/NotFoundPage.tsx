import { Link } from 'react-router-dom';

const NotFoundPage = () => {
  return (
    <div className="relative" style={{ height: 'calc(100vh - 130px)' }}>
      <div className="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 text-center">
        <div>
          <span className="luckiest_guy text-6xl">404 PAGE NOT FOUND</span>
        </div>
        <Link to="/">
          <div className="bg-ssapick rounded-lg luckiest_guy mt-6 mb-3 p-2">
            GO TO LOGIN PAGE!
          </div>
        </Link>
        <Link to="/home">
          <div className="bg-white hover:bg-white/80 rounded-lg luckiest_guy mb-4 p-2">
            GO TO HOME PAGE!
          </div>
        </Link>
      </div>
    </div>
  );
};

export default NotFoundPage;
