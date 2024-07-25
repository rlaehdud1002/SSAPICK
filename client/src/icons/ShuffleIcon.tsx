interface ShuffleIconProps {
  className: string;
}

const ShuffleIcon = ({ className }: ShuffleIconProps) => {
  return (
    <svg
      width="20"
      height="20"
      viewBox="0 0 20 20"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
      className={className}
    >
      <circle cx="10" cy="10" r="10" fill="white" fill-opacity="0.5" />
      <g clip-path="url(#clip0_637_1554)">
        <path
          d="M9.48893 8.50003C9.27495 8.21206 9.00142 7.9736 8.68697 7.80088C8.37251 7.62816 8.02452 7.52524 7.66671 7.49915H5.33337"
          stroke="#000855"
          strokeWidth="1.5"
          strokeLinecap="round"
          strokeLinejoin="round"
        />
        <path
          d="M15.3334 7.49915H13C11.3334 7.49915 10.3334 9.83248 10.3334 9.83248C10.3334 9.83248 9.33337 12.1658 7.66671 12.1658H5.33337"
          stroke="#000855"
          strokeWidth="1.5"
          strokeLinecap="round"
          strokeLinejoin="round"
        />
        <path
          d="M11.1777 11.1666C11.3917 11.4544 11.6653 11.6927 11.9798 11.8651C12.2943 12.0375 12.6422 12.1401 13 12.1657H15.3333"
          stroke="#000855"
          strokeWidth="1.5"
          strokeLinecap="round"
          strokeLinejoin="round"
        />
        <path
          d="M14 6.16577L15.3333 7.4991L14 8.83244"
          stroke="#000855"
          strokeWidth="1.5"
          strokeLinecap="round"
          strokeLinejoin="round"
        />
        <path
          d="M14 10.8324L15.3333 12.1657L14 13.4991"
          stroke="#000855"
          strokeWidth="1.5"
          strokeLinecap="round"
          strokeLinejoin="round"
        />
      </g>
      <defs>
        <clipPath id="clip0_637_1554">
          <rect
            width="10.6667"
            height="10.6667"
            fill="white"
            transform="translate(5 4.5)"
          />
        </clipPath>
      </defs>
    </svg>
  );
};

export default ShuffleIcon;
