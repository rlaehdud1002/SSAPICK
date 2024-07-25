interface CoinIconProps {
  width: number;
  height: number;
  className?: string;
}

const CoinIcon = ({ width, height, className }: CoinIconProps) => {
  return (
    <img
      className={`cursor-pointer, ${className}`}
      width={width}
      height={height}
      src="/icons/Coin.png"
      alt="coin"
    />
  );
};

export default CoinIcon;
