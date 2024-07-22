interface MessageAlarmIconProps {
  width: number;
  height: number;
}

const MessageAlarmIcon = ({ width, height }: MessageAlarmIconProps) => {
  return (
    <img
      className="cursor-pointer"
      width={width}
      height={height}
      src="/icons/Message.png"
      alt="Message"
    />
  );
};

export default MessageAlarmIcon;
