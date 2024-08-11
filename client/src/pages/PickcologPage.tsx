import { useNavigate } from "react-router-dom";

const Pickcolog = () => {
  const nav = useNavigate();

  return (
    <div className="m-6">
      <div className="flex flex-row items-center m-2 cursor-pointer" onClick={() => nav(-1)}>
        ss
      </div>
    </div>
  );
};

export default Pickcolog;
