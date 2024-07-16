interface MessageIconProps {
  isHighlighted: boolean;
}

const MessageIcon = ({ isHighlighted }: MessageIconProps) => {
  return (
    <svg
      width="26"
      height="26"
      viewBox="0 0 26 26"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
    >
      <rect
        x="4.3335"
        y="6.5"
        width="17.3333"
        height="13"
        rx="1.02401"
        stroke="black"
        stroke-width="2"
      />
      <path
        d="M4.3335 9.75L12.817 13.9917C12.9323 14.0494 13.068 14.0494 13.1833 13.9917L21.6668 9.75"
        stroke="black"
        stroke-width="2"
      />
    </svg>
  );
};

export default MessageIcon;
