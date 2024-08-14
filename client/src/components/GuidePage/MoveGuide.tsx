import GuideIcon from "icons/GuideIcon";
import { useNavigate } from "react-router-dom";

const MoveGuide = () => {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate("/guide");
  };

  const isMain = window.location.pathname === "/";

  console.log(isMain);

  return (
    <>
      {isMain ? (
        <div
          onClick={handleClick}
          className="luckiest_guy w-72 h-14 cursor-pointer bg-white/70 hover:bg-blue-600 text-[#5f86e9] font-bold py-2 px-4 rounded-lg text-center mx-auto shadow-xs transition-colors duration-300 ease-in-out"
        >
          <p className="text-2xl mt-1">USER GUIDE</p>
        </div>
      ) : (
        <div onClick={handleClick}>
          <GuideIcon />
        </div>
      )}
    </>
  );
};

export default MoveGuide;
