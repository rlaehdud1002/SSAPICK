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
          className="luckiest_guy mb-10 cursor-pointer bg-white/70 hover:bg-blue-600 text-[#5f86e9] font-bold py-2 px-4 rounded-full text-center max-w-xs mx-auto shadow-lg transition-colors duration-300 ease-in-out"
        >
          <p className="text-2xl">GUIDE</p>
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
