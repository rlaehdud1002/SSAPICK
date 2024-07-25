interface BlockIconProps {
  width: number;
  height: number;
}

const BlockIcon = ({width, height}:BlockIconProps) => {
  return <img 
  width={width}
  height={height}
  src="icons/Block.png" 
  alt="block" />
}

export default BlockIcon;