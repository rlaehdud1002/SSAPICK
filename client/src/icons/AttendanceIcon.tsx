interface AttendanceIconProps {
  width: number;
  height: number;
}

const AttendanceIcon = ({width, height}:AttendanceIconProps) => {
  return <img
  width={width}
  height={height}
  src="icons/Attend.png" 
  alt="attendance" />
}

export default AttendanceIcon;