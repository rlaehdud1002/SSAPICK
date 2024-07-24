interface ProfileIconProps {
  isHighlighted: boolean;
}

const ProfileIcon = ({ isHighlighted }: ProfileIconProps) => {
  return (
    <>
      {isHighlighted ? (
        <svg
          width="26"
          height="26"
          viewBox="0 0 26 26"
          fill="none"
          xmlns="http://www.w3.org/2000/svg"
        >
          <ellipse
            cx="12.9998"
            cy="8.66665"
            rx="4.33333"
            ry="4.33333"
            fill="#5F86E9"
          />
          <path
            d="M5.87613 18.3884C6.60283 15.611 9.3549 14.0833 12.2258 14.0833H13.7739C16.6448 14.0833 19.3968 15.611 20.1235 18.3884C20.3125 19.1106 20.4631 19.882 20.5358 20.668C20.5866 21.218 20.1355 21.6666 19.5832 21.6666H6.4165C5.86422 21.6666 5.41306 21.218 5.46391 20.668C5.53657 19.882 5.68715 19.1107 5.87613 18.3884Z"
            fill="#5F86E9"
          />
        </svg>
      ) : (
        <svg
          width="26"
          height="26"
          viewBox="0 0 26 26"
          fill="none"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path
            d="M21.3715 22.1511C20.8777 20.7689 19.7897 19.5476 18.2761 18.6766C16.7625 17.8055 14.908 17.3334 13.0002 17.3334C11.0923 17.3334 9.23782 17.8055 7.72423 18.6766C6.21065 19.5476 5.12259 20.7689 4.62881 22.151"
            stroke="black"
            strokeWidth="2"
            strokeLinecap="round"
          />
          <circle
            cx="13.0003"
            cy="8.66671"
            r="4.33333"
            stroke="black"
            strokeWidth="2"
            strokeLinecap="round"
          />
        </svg>
      )}
    </>
  );
};

export default ProfileIcon;
