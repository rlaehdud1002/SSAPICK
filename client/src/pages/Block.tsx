import { Link, Outlet, useLocation } from 'react-router-dom';

const Block = () => {
  const location = useLocation().pathname.split('/')[3];
  return ( 
    <div className="m-5">
      <div className="flex flex-row justify-center">
        <Link
          to="blockfriend"
          className={`border-b-2 w-1/2 text-center pb-3 text-lg ${location === 'blockfriend' || location === undefined ? 'text-color-5F86E9 border-[#5F86E9]' : 'text-white border-white'}`}
        >
          차단된 친구
        </Link>
        <Link
          to="blockquestion"
          className={`border-b-2 w-1/2 text-center pb-3 text-lg ${location === 'blockquestion' ? 'text-color-5F86E9 border-[#5F86E9]' : 'text-white border-white'}`}
        >
          차단된 질문
        </Link>
      </div>
      <div className="mt-5">
        <Outlet />
      </div>
    </div>
  );
}

export default Block;