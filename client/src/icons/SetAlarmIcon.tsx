interface SetAlarmIconProps {
  width: number;
  height: number;
  alarmset?: boolean;
  className?: string;
}

const SetAlarmIcon = ({
  width,
  height,
  alarmset,
  className,
}: SetAlarmIconProps) => {
  return (
    <div className={className}>
      {alarmset ? (
        <img
          width={width}
          height={height}
          src="/icons/SetAlarm.png"
          alt="alarm"
        />
      ) : (
        <img width={width} height={height} src="/icons/Alarm.png" alt="alarm" />
      )}
    </div>
  );
};

export default SetAlarmIcon;
