interface PlusIconProps {
  className?: string;
}

const PlusIcon = ({ className }: PlusIconProps) => {
  return (
    <svg
      width="20"
      height="20"
      viewBox="0 0 20 20"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
      className={className}
    >
      <g opacity="0.5" clipPath="url(#clip0_815_1574)">
        <path
          d="M10 7.75V12.25"
          stroke="white"
          strokeWidth="1.5"
          strokeLinecap="round"
          strokeLinejoin="round"
        />
        <path
          d="M7.75 10H12.25"
          stroke="white"
          strokeWidth="1.5"
          strokeLinecap="round"
          strokeLinejoin="round"
        />
        <path
          d="M4.375 10C4.375 11.4918 4.96763 12.9226 6.02252 13.9775C7.07742 15.0324 8.50816 15.625 10 15.625C11.4918 15.625 12.9226 15.0324 13.9775 13.9775C15.0324 12.9226 15.625 11.4918 15.625 10C15.625 8.50816 15.0324 7.07742 13.9775 6.02252C12.9226 4.96763 11.4918 4.375 10 4.375C8.50816 4.375 7.07742 4.96763 6.02252 6.02252C4.96763 7.07742 4.375 8.50816 4.375 10Z"
          stroke="white"
          strokeWidth="1.5"
          strokeLinecap="round"
          strokeLinejoin="round"
        />
      </g>
      <defs>
        <clipPath id="clip0_815_1574">
          <rect
            width="12"
            height="12"
            fill="white"
            transform="translate(4 4)"
          />
        </clipPath>
      </defs>
    </svg>
  );
};

export default PlusIcon;
