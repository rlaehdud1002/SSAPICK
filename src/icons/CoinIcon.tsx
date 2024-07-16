interface CoinIconProps {
  width: number;
  height: number;
}

const CoinIcon = ({ width, height }: CoinIconProps) => {
  return (
    <img
      className="cursor-pointer"
      width={width}
      height={height}
      src="/icons/coin.png"
      alt="coin"
    />
  );
};

export default CoinIcon;
