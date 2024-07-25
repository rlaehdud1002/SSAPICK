interface MessageQuestionIconProps {
  className?: string;
}

const MessageQuestionIcon = ({ className }: MessageQuestionIconProps) => {
  return (
    <svg
      width="20"
      height="20"
      viewBox="0 0 20 20"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
      className={className}
    >
      <path
        d="M16.1049 4.81493C16.6666 5.65567 16.6666 6.82606 16.6666 9.16683C16.6666 11.5076 16.6666 12.678 16.1049 13.5187C15.8617 13.8827 15.5492 14.1952 15.1852 14.4384C14.4508 14.9291 13.4647 14.9912 11.6666 14.999V15.0002L10.7453 16.8428C10.4382 17.457 9.56173 17.457 9.25462 16.8428L8.33331 15.0002V14.999C6.53523 14.9912 5.54921 14.9291 4.81475 14.4384C4.45078 14.1952 4.13828 13.8827 3.89508 13.5187C3.33331 12.678 3.33331 11.5076 3.33331 9.16683C3.33331 6.82606 3.33331 5.65567 3.89508 4.81493C4.13828 4.45096 4.45078 4.13846 4.81475 3.89526C5.65549 3.3335 6.82588 3.3335 9.16665 3.3335H10.8333C13.1741 3.3335 14.3445 3.3335 15.1852 3.89526C15.5492 4.13846 15.8617 4.45096 16.1049 4.81493Z"
        stroke="#6873DD"
        strokeWidth="2"
        strokeLinecap="round"
        strokeLinejoin="round"
      />
      <path
        d="M7.5 7.5L12.5 7.5"
        stroke="#6873DD"
        strokeWidth="2"
        strokeLinecap="round"
        strokeLinejoin="round"
      />
      <path
        d="M7.5 10.8335H10"
        stroke="#6873DD"
        strokeWidth="2"
        strokeLinecap="round"
        strokeLinejoin="round"
      />
    </svg>
  );
};

export default MessageQuestionIcon;
