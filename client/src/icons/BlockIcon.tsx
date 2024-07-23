import { useNavigate } from "react-router-dom";

interface BlockIconProps {
  width: number;
  height: number;
}

const BlockIcon = ({width, height}:BlockIconProps) => {
  const navigate = useNavigate();
  const navigateToBlock = () => {
    navigate('/Block');
  }
  return <img 
  onClick={navigateToBlock}
  width={width}
  height={height}
  src="icons/Block.png" 
  alt="block" />
}

export default BlockIcon;