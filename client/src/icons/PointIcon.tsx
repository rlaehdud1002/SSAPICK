const PointIcon = () => {
  return (
    <svg
      width="24"
      height="24"
      viewBox="0 0 24 24"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
      className="cursor-pointer"
    >
      <circle
        cx="12"
        cy="12"
        r="1"
        transform="rotate(-90 12 12)"
        fill="#000855"
        stroke="#000855"
        strokeWidth="1.5"
        strokeLinecap="round"
      />
      <circle
        cx="12"
        cy="18"
        r="1"
        transform="rotate(-90 12 18)"
        fill="#000855"
        stroke="#000855"
        strokeWidth="1.5"
        strokeLinecap="round"
      />
      <circle
        cx="12"
        cy="6"
        r="1"
        transform="rotate(-90 12 6)"
        fill="#000855"
        stroke="#000855"
        strokeWidth="1.5"
        strokeLinecap="round"
      />
    </svg>
  );
};

export default PointIcon;
