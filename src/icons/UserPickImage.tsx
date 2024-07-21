interface UserPickImageProps {
  width: number;
  height: number;
}

const UserPickImage = ({ width, height }: UserPickImageProps) => {
  return (
    <img
      className="cursor-pointer"
      width={width}
      height={height}
      src="icons/profile.png"
      alt="profileImage"
    />
  );
};

export default UserPickImage;
