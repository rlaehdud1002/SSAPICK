const Loading = () => {
  return (
    <div
      style={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        height: "60vh",
        width: "100%",
      }}
    >
      <svg
        xmlns="http://www.w3.org/2000/svg"
        viewBox="0 0 100 100"
        preserveAspectRatio="xMidYMid"
        width="70"
        height="70"
        style={{
          shapeRendering: "auto",
          display: "block",
          background: "transparent",
        }}
        xmlnsXlink="http://www.w3.org/1999/xlink"
      >
        <g>
          <circle fill="#5f86e9" r="10" cy="50" cx="84">
            <animate
              begin="0s"
              keySplines="0 0.5 0.5 1"
              values="8;0"
              keyTimes="0;1"
              calcMode="spline"
              dur="0.5681818181818182s"
              repeatCount="indefinite"
              attributeName="r"
            ></animate>
            <animate
              begin="0s"
              values="#5f86e9;#e6f4f1;#f8f9ff;#3969c8;#5f86e9"
              keyTimes="0;0.25;0.5;0.75;1"
              calcMode="discrete"
              dur="2.272727272727273s"
              repeatCount="indefinite"
              attributeName="fill"
            ></animate>
          </circle>
          <circle fill="#5f86e9" r="10" cy="50" cx="16">
            <animate
              begin="0s"
              keySplines="0 0.5 0.5 1;0 0.5 0.5 1;0 0.5 0.5 1;0 0.5 0.5 1"
              values="0;0;8;8;8"
              keyTimes="0;0.25;0.5;0.75;1"
              calcMode="spline"
              dur="2.272727272727273s"
              repeatCount="indefinite"
              attributeName="r"
            ></animate>
            <animate
              begin="0s"
              keySplines="0 0.5 0.5 1;0 0.5 0.5 1;0 0.5 0.5 1;0 0.5 0.5 1"
              values="16;16;16;50;84"
              keyTimes="0;0.25;0.5;0.75;1"
              calcMode="spline"
              dur="2.272727272727273s"
              repeatCount="indefinite"
              attributeName="cx"
            ></animate>
          </circle>
          <circle fill="#3969c8" r="10" cy="50" cx="50">
            <animate
              begin="-0.5681818181818182s"
              keySplines="0 0.5 0.5 1;0 0.5 0.5 1;0 0.5 0.5 1;0 0.5 0.5 1"
              values="0;0;8;8;8"
              keyTimes="0;0.25;0.5;0.75;1"
              calcMode="spline"
              dur="2.272727272727273s"
              repeatCount="indefinite"
              attributeName="r"
            ></animate>
            <animate
              begin="-0.5681818181818182s"
              keySplines="0 0.5 0.5 1;0 0.5 0.5 1;0 0.5 0.5 1;0 0.5 0.5 1"
              values="16;16;16;50;84"
              keyTimes="0;0.25;0.5;0.75;1"
              calcMode="spline"
              dur="2.272727272727273s"
              repeatCount="indefinite"
              attributeName="cx"
            ></animate>
          </circle>
          <circle fill="#f8f9ff" r="10" cy="50" cx="84">
            <animate
              begin="-1.1363636363636365s"
              keySplines="0 0.5 0.5 1;0 0.5 0.5 1;0 0.5 0.5 1;0 0.5 0.5 1"
              values="0;0;8;8;8"
              keyTimes="0;0.25;0.5;0.75;1"
              calcMode="spline"
              dur="2.272727272727273s"
              repeatCount="indefinite"
              attributeName="r"
            ></animate>
            <animate
              begin="-1.1363636363636365s"
              keySplines="0 0.5 0.5 1;0 0.5 0.5 1;0 0.5 0.5 1;0 0.5 0.5 1"
              values="16;16;16;50;84"
              keyTimes="0;0.25;0.5;0.75;1"
              calcMode="spline"
              dur="2.272727272727273s"
              repeatCount="indefinite"
              attributeName="cx"
            ></animate>
          </circle>
          <circle fill="#e6f4f1" r="10" cy="50" cx="16">
            <animate
              begin="-1.7045454545454546s"
              keySplines="0 0.5 0.5 1;0 0.5 0.5 1;0 0.5 0.5 1;0 0.5 0.5 1"
              values="0;0;8;8;8"
              keyTimes="0;0.25;0.5;0.75;1"
              calcMode="spline"
              dur="2.272727272727273s"
              repeatCount="indefinite"
              attributeName="r"
            ></animate>
            <animate
              begin="-1.7045454545454546s"
              keySplines="0 0.5 0.5 1;0 0.5 0.5 1;0 0.5 0.5 1;0 0.5 0.5 1"
              values="16;16;16;50;84"
              keyTimes="0;0.25;0.5;0.75;1"
              calcMode="spline"
              dur="2.272727272727273s"
              repeatCount="indefinite"
              attributeName="cx"
            ></animate>
          </circle>
          <g></g>
        </g>
      </svg>
    </div>
  );
};

export default Loading;
