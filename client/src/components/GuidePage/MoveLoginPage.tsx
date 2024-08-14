import { useNavigate } from "react-router-dom";

const MoveLoginPage = () => {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate("/login");
  };

  return (
    <div
      onClick={handleClick}
      className="mt-5 cursor-pointer bg-[#5f86e9] hover:bg-blue-600 text-white font-bold py-2 px-4 rounded-full text-center max-w-xs mx-auto shadow-lg transition-colors duration-300 ease-in-out"
    >
      로그인 하러 가기
    </div>
  );
};

export default MoveLoginPage;
