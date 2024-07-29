interface WarningIconProps {
  className?: string;
  width: number;
  height: number;
  circle?: boolean;
}

const WarningIcon = ({
  className,
  width,
  height,
  circle,
}: WarningIconProps) => {
  return (
      <svg
        width={width}
        height={height}
        viewBox="0 0 20 20"
        className={className}
        fill="none"
        xmlns="http://www.w3.org/2000/svg"
      >
        {circle && (
          <circle
            cx={width / 2}
            cy={height / 2}
            r={width / 2}
            fill="white"
            fill-opacity="0.5"
          />
        )}

        <path
          d="M14.2619 13.762L5.59521 13.762"
          stroke="#FF0000"
          stroke-width="1.2381"
          stroke-linecap="round"
          stroke-linejoin="round"
        />
        <path
          d="M6.83337 13.762L7.35075 9.62297C7.40883 9.15828 7.80385 8.80957 8.27215 8.80957H11.5851C12.0534 8.80957 12.4484 9.15828 12.5065 9.62297L13.0238 13.762"
          stroke="#FF0000"
          stroke-width="1.2381"
          stroke-linecap="round"
          stroke-linejoin="round"
        />
        <path
          d="M9.92859 4.4762L9.9286 6.33334"
          stroke="#FF0000"
          stroke-width="1.2381"
          stroke-linecap="round"
          stroke-linejoin="round"
        />
        <path
          d="M15.1904 7.57141L13.6428 8.19046M4.66663 7.57141L6.21424 8.19046"
          stroke="#FF0000"
          stroke-width="1.2381"
          stroke-linecap="round"
          stroke-linejoin="round"
        />
        <path
          d="M13.3333 5.40479L12.4048 6.64288M6.5238 5.40479L7.45237 6.64288"
          stroke="#FF0000"
          stroke-width="1.2381"
          stroke-linecap="round"
          stroke-linejoin="round"
        />
        <path
          d="M9.92859 10.6666L10.8572 10.6666"
          stroke="#FF0000"
          stroke-width="1.2381"
          stroke-linecap="round"
          stroke-linejoin="round"
        />
      </svg>
  );
};

export default WarningIcon;
