interface WarningIconProps {
  className?: string;
}

const WarningIcon = ({ className }: WarningIconProps) => {
  return (
    <img
      src="/icons/Warning.png"
      alt=""
      width={25}
      height={25}
      className={className}
    />
  );
};

export default WarningIcon;
