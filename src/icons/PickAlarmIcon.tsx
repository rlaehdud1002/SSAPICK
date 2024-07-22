interface PickAlarmIconProps {
  width: number;
  height: number;
}

const PickAlarmIcon = ({ width, height }: PickAlarmIconProps) => {
  return (
    <img
      className="cursor-pointer"
      width={width}
      height={height}
      src="/icons/PickAlarm.png"
      alt="Pick"
    />
  );
};

export default PickAlarmIcon;
