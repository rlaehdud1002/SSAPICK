interface UserPickIconProps {
  gen: string;
}

const UserPickIcon = ({ gen }: UserPickIconProps) => {
  return (
    <svg
      width="35"
      height="35"
      viewBox="0 0 35 35"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
    >
      <circle cx="17.5" cy="17.5" r="17.5" fill="white" fill-opacity="0.5" />
      <path
        d="M24.036 24.6448C23.6505 23.5657 22.801 22.6121 21.6192 21.932C20.4375 21.2519 18.9895 20.8833 17.4999 20.8833C16.0104 20.8833 14.5624 21.2519 13.3807 21.932C12.1989 22.6121 11.3494 23.5657 10.9638 24.6448"
        stroke={gen === 'male' ? '#7EAFFF' : '#FF9798'}
        stroke-width="1.76522"
        stroke-linecap="round"
      />
      <circle
        cx="17.5"
        cy="14.1167"
        r="3.38333"
        stroke={gen === 'male' ? '#7EAFFF' : '#FF9798'}
        stroke-width="1.76522"
        stroke-linecap="round"
      />
    </svg>
  );
};

export default UserPickIcon;
