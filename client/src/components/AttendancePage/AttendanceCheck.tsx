interface AttendanceCheckProps {
  date: number;
}

const AttendanceCheck = ({ date }: AttendanceCheckProps) => {
  const circleArr = [
    { cx: 34, cy: 21, num: 1 },
    { cx: 106, cy: 22, num: 2 },
    { cx: 177, cy: 20, num: 3 },
    { cx: 248, cy: 21, num: 4 },
    { cx: 298, cy: 54, num: 5 },
    { cx: 240, cy: 85, num: 6 },
    { cx: 82, cy: 85, num: 8 },
    { cx: 20, cy: 118, num: 9 },
    { cx: 73, cy: 149, num: 10 },
    { cx: 145, cy: 149, num: 11 },
    { cx: 217, cy: 149, num: 12 },
    { cx: 288, cy: 149, num: 13 },
  ];

  circleArr.map(({ cx, cy, num }) => console.log(cx, cy, num));

  return (
    <svg
      width="350"
      height="212.58"
      viewBox="0 0 318 169"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
      className="mx-auto my-8"
    >
      <line
        x1="52.9964"
        y1="20.7832"
        x2="267.996"
        y2="19.7496"
        stroke="#5F86E9"
        strokeWidth="1.5"
      />
      <line
        x1="52.9964"
        y1="85.25"
        x2="267.996"
        y2="84.2164"
        stroke="#5F86E9"
        strokeWidth="1.5"
      />
      <line
        x1="52.9964"
        y1="149.783"
        x2="267.996"
        y2="148.75"
        stroke="#5F86E9"
        strokeWidth="1.5"
      />
      <path
        d="M268 19.75C276.553 19.75 284.756 23.1478 290.804 29.1958C296.852 35.2439 300.25 43.4468 300.25 52C300.25 60.5532 296.852 68.7561 290.804 74.8042C284.756 80.8522 276.553 84.25 268 84.25"
        stroke="#5F86E9"
        strokeWidth="1.5"
      />
      <path
        d="M53 149.75C44.4468 149.75 36.2439 146.352 30.1958 140.304C24.1478 134.256 20.75 126.053 20.75 117.5C20.75 108.947 24.1478 100.744 30.1958 94.6958C36.2439 88.6478 44.4468 85.25 53 85.25"
        stroke="#5F86E9"
        strokeWidth="1.5"
      />

      {/* 출석 circle */}
      {circleArr.map(({ cx, cy, num }) => (
        <g key={num}>
          <circle
            cx={cx}
            cy={cy}
            r="20"
            fill={date >= num ? '#6F7CFF' : '#E2E3F4'}
          />
          <text
            x={cx}
            y={cy}
            textAnchor="middle"
            dy=".3em"
            fill="white"
            fontSize="22.67"
            className="luckiest_guy"
          >
            {num}
          </text>
        </g>
      ))}

      {/* 7일째는 circle 크기가 다름 */}
      <circle
        cx="161"
        cy="85"
        r="23"
        fill={date >= 7 ? '#6F7CFF' : '#E2E3F4'}
      />
      <text
        x="161"
        y="85"
        textAnchor="middle"
        dy=".3em"
        fill="white"
        fontSize="15"
        className="luckiest_guy"
      >
        SSAPICK!
      </text>
    </svg>
  );
};

export default AttendanceCheck;
