interface ProfileIconProps {
  isHighlighted: boolean;
}

const ProfileIcon = ({ isHighlighted }: ProfileIconProps) => {
  return (
    <svg
      width="26"
      height="26"
      viewBox="0 0 26 26"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
    >
      <path
        d="M21.3715 22.151C20.8777 20.7689 19.7897 19.5476 18.2761 18.6765C16.7625 17.8055 14.908 17.3333 13.0002 17.3333C11.0923 17.3333 9.23782 17.8055 7.72423 18.6765C6.21065 19.5476 5.12259 20.7689 4.62881 22.151"
        stroke="black"
        stroke-width="2"
        stroke-linecap="round"
      />
      <circle
        cx="13.0003"
        cy="8.66668"
        r="4.33333"
        stroke="black"
        stroke-width="2"
        stroke-linecap="round"
      />
    </svg>
  );
};

export default ProfileIcon;
