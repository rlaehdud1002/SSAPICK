import { useNavigate } from "react-router-dom";

const MoveBeforePage = () => {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate(-1);
  };

  return (
    <div
      onClick={handleClick}
      className="mt-5 cursor-pointer bg-[#5f86e9] hover:bg-blue-600 text-white font-bold py-2 px-4 rounded-full text-center max-w-xs mx-auto shadow-lg transition-colors duration-300 ease-in-out"
    >
      돌아가기
    </div>
  );
};

export default MoveBeforePage;
