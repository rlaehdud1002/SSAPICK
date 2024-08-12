interface PassIconProps {
  className?: string;
}

const PassIcon = ({ className }: PassIconProps) => {
  return (
    <svg
      width="20"
      height="20"
      viewBox="0 0 25 25"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
      className={className}
    >
      <path d="M12 18L18 12L12 6" stroke="white" stroke-width="1.5" />
      <path d="M6 18L12 12L6 6" stroke="white" stroke-width="1.5" />
    </svg>
  );
};

export default PassIcon;
