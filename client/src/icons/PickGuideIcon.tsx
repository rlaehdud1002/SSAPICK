interface PickAlarmIconProps {
  width: number;
  height: number;
}

const PickGuideIcon = ({ width, height }: PickAlarmIconProps) => {
  return <img width={width} height={height} src="icons/PickGuide1.png" alt="Pick" />;
};

export default PickGuideIcon;
