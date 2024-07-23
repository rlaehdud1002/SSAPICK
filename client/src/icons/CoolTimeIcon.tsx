interface CoolTimeIconProps {
  width: number;
  height: number;
  className: string;
}

const CoolTimeIcon = ({ width, height, className }: CoolTimeIconProps) => {
  return (
    <img
      className={className}
      width={width}
      height={height}
      src="/icons/CoolTime.png"
      alt="coin"
    />
  );
};

export default CoolTimeIcon;
