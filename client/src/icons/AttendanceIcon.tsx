interface AttendanceIconProps {
  width: number;
  height: number;
  setpage?: boolean;
}
const AttendanceIcon = ({ width, height, setpage }: AttendanceIconProps) => {
  return (
    <>
      {setpage ? (
        <img
          width={width}
          height={height}
          src="/icons/Attendance.png"
          alt="attendance"
        />
      ) : (
        <img
          width={width}
          height={height}
          src="/icons/Attend.png"
          alt="attend"
        />
      )}
    </>
  );
};

export default AttendanceIcon;
