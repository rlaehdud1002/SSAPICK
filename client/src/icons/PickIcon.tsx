interface PickIconProps {
  isHighlighted: boolean;
}

const PickIcon = ({ isHighlighted }: PickIconProps) => {
  return (
    <svg
      width="47"
      height="46"
      viewBox="0 0 47 46"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
    >
      <path
        d="M17.6248 19.1667L25.3083 24.8067C25.3933 24.8691 25.5118 24.8567 25.5821 24.7781L39.1665 9.58337"
        stroke={isHighlighted ? '#5F86E9' : '#000000'}
        stroke-width="3"
        stroke-linecap="round"
      />
      <path
        d="M41.125 23C41.125 26.6043 39.9715 30.118 37.8264 33.0477C35.6814 35.9775 32.6526 38.176 29.1654 39.3345C25.6782 40.4931 21.9078 40.5535 18.3837 39.5072C14.8597 38.461 11.759 36.3606 9.51715 33.5011C7.27531 30.6417 6.00493 27.1667 5.88444 23.5644C5.76394 19.9621 6.79939 16.4133 8.84535 13.4164C10.8913 10.4196 13.845 8.12523 17.2916 6.85562C20.7382 5.58602 24.5045 5.40492 28.0617 6.33778"
        stroke={isHighlighted ? '#5F86E9' : '#000000'}
        stroke-width="3"
        stroke-linecap="round"
      />
    </svg>
  );
};

export default PickIcon;
