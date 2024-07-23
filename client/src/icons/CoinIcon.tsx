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
      src="/icons/Coin.png"
      alt="coin"
    />
  );
};

export default CoinIcon;
