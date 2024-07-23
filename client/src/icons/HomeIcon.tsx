interface HomeIconProps {
  isHighlighted: boolean;
}

const HomeIcon = ({ isHighlighted }: HomeIconProps) => {
  return (
    <svg
      width="25"
      height="23"
      viewBox="0 0 25 23"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
    >
      <path
        d="M4 13.1329V21.3829H10V15.3829C10 14.985 10.158 14.6035 10.4393 14.3222C10.7206 14.0409 11.1022 13.8829 11.5 13.8829H13C13.3978 13.8829 13.7794 14.0409 14.0607 14.3222C14.342 14.6035 14.5 14.985 14.5 15.3829V21.3829H20.5V13.1329"
        fill={isHighlighted ? '#5F86E9' : 'none'}
      />
      <path
        d="M4 13.1329V21.3829H10V15.3829C10 14.985 10.158 14.6035 10.4393 14.3222C10.7206 14.0409 11.1022 13.8829 11.5 13.8829H13C13.3978 13.8829 13.7794 14.0409 14.0607 14.3222C14.342 14.6035 14.5 14.985 14.5 15.3829V21.3829H20.5V13.1329"
        stroke={isHighlighted ? '#5F86E9' : '#000000'}
        stroke-width="2.09"
        stroke-linecap="round"
        stroke-linejoin="round"
      />
      <path
        d="M1 11.6329L11.19 1.44286C11.3286 1.30257 11.4936 1.19119 11.6756 1.11517C11.8576 1.03915 12.0528 1 12.25 1C12.4472 1 12.6424 1.03915 12.8244 1.11517C13.0064 1.19119 13.1714 1.30257 13.31 1.44286L23.5 11.6329"
        fill={isHighlighted ? '#5F86E9' : 'none'}
      />
      <path
        d="M1 11.6329L11.19 1.44286C11.3286 1.30257 11.4936 1.19119 11.6756 1.11517C11.8576 1.03915 12.0528 1 12.25 1C12.4472 1 12.6424 1.03915 12.8244 1.11517C13.0064 1.19119 13.1714 1.30257 13.31 1.44286L23.5 11.6329"
        stroke={isHighlighted ? '#5F86E9' : '#000000'}
        stroke-width="2.09"
        stroke-linecap="round"
        stroke-linejoin="round"
      />
      <path
        d="M16.75 4.88286V1.96286H20.5V8.63286"
        fill={isHighlighted ? '#5F86E9' : 'none'}
      />
      <path
        d="M16.75 4.88286V1.96286H20.5V8.63286"
        stroke={isHighlighted ? '#5F86E9' : '#000000'}
        stroke-width="2.09"
        stroke-linecap="round"
        stroke-linejoin="round"
      />
    </svg>
  );
};

export default HomeIcon;
