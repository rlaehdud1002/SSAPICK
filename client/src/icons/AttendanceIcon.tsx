import { useNavigate } from "react-router-dom";

interface AttendanceIconProps {
  width: number;
  height: number;
}

const AttendanceIcon = ({width, height}:AttendanceIconProps) => {
  const navigate = useNavigate();
  const navigateToAttendance = () => {
    navigate('/Attendance');
  }
  return <img 
  onClick={navigateToAttendance}
  width={width}
  height={height}
  src="icons/Attend.png" 
  alt="attendance" />
}

export default AttendanceIcon;